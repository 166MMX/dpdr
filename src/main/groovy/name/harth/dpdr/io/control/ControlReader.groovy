package name.harth.dpdr.io.control

import name.harth.dpdr.model.control.BasicDebSrcIdxParagraph
import name.harth.dpdr.model.control.DependencyEntry
import name.harth.dpdr.model.control.DependencyType
import name.harth.dpdr.model.control.FileEntry
import name.harth.dpdr.model.control.VcsAddressEntry
import name.harth.dpdr.model.control.VcsAddressType
import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.apache.commons.compress.compressors.bzip2.BZip2Utils
import org.apache.commons.compress.compressors.gzip.GzipUtils
import org.apache.commons.compress.compressors.xz.XZUtils
import org.slf4j.LoggerFactory

import javax.mail.Header
import javax.mail.internet.InternetHeaders
import java.util.regex.Matcher
import java.util.regex.Pattern

class ControlReader
{
    private static final logger = LoggerFactory.getLogger(ControlReader.class)

    static final Pattern VALUE_LIST_DELIMITER = ~/\s*,\s*(?=(?:[^"]|"(?:[^\\"]|\\\\|\\")*?")*$)/
    public static final Pattern SIMPLE_INTERNET_ADDRESS = ~/["']?([^<>]+)["']? <([^<]+@[^>]+)>(?:\s*,?\s*)/

    static InputStream uncompressedInputStream(InputStream inputStream, String fileName)
    {
        CompressorStreamFactory compressorStreamFactory = new CompressorStreamFactory()
        InputStream compressorInputStream = inputStream
        String uncompressedFileName = fileName

        while (BZip2Utils.isCompressedFilename(uncompressedFileName) || GzipUtils.isCompressedFilename(uncompressedFileName) || XZUtils.isCompressedFilename(uncompressedFileName))
        {
            if (BZip2Utils.isCompressedFilename(uncompressedFileName))
            {
                uncompressedFileName = BZip2Utils.getUncompressedFilename(uncompressedFileName)
                compressorInputStream = compressorStreamFactory.createCompressorInputStream(CompressorStreamFactory.BZIP2, compressorInputStream)
            }
            else if (GzipUtils.isCompressedFilename(uncompressedFileName))
            {
                uncompressedFileName = GzipUtils.getUncompressedFilename(uncompressedFileName)
                compressorInputStream = compressorStreamFactory.createCompressorInputStream(CompressorStreamFactory.GZIP, compressorInputStream)
            }
            else if (XZUtils.isCompressedFilename(uncompressedFileName))
            {
                uncompressedFileName = XZUtils.getUncompressedFilename(uncompressedFileName)
                compressorInputStream = compressorStreamFactory.createCompressorInputStream(CompressorStreamFactory.XZ, compressorInputStream)
            }
        }

        return compressorInputStream
    }

    static List<Enumeration<Header>> readIdx(InputStream inputStream)
    {
        List<Enumeration<Header>> result = new ArrayList<Enumeration<Header>>()
        try
        {
            while (inputStream.available())
            {
                result.add(
                    new InternetHeaders(inputStream).allHeaders
                )
            }
        }
        catch (IOException ex)
        {
            logger.debug('Possibly reached EOF', ex)
        }
        return result
    }

    static List<BasicDebSrcIdxParagraph> readSrcIdx (File file)
    {
        InputStream inputStream = new FileInputStream(file)
        inputStream = uncompressedInputStream(inputStream, file.name)
        inputStream = new BufferedInputStream(inputStream)
        List<BasicDebSrcIdxParagraph> result = readSrcIdx(inputStream)
        return result
    }

    static List<BasicDebSrcIdxParagraph> readSrcIdx (InputStream inputStream)
    {
        List<Enumeration<Header>> headersList = readIdx(inputStream)
        List<BasicDebSrcIdxParagraph> result = new ArrayList<BasicDebSrcIdxParagraph>()
        headersList.each {
            result.add(
                readSrcIdx(it)
            )
        }
        return result
    }

    static BasicDebSrcIdxParagraph readSrcIdx(Enumeration<Header> headers)
    {
        BasicDebSrcIdxParagraph result = new BasicDebSrcIdxParagraph()
        headers.toList().each {
            switch (it.name)
            {
            // mandatory
                case 'Format':
                    result.format = it.value
                    break

            // mandatory
                case 'Package':
                    result.name = it.value
                    break

            // optional
                case 'Binary':
                    result.binary.addAll(
                        readValueList(it)
                    )
                    break

            // optional
                case 'Architecture':
                    result.architecture = it.value
                    break

            // mandatory
                case 'Version':
                    result.version = it.value
                    break

            // mandatory
                case 'Maintainer':
                    result.maintainer = it.value
                    break

            // optional
                case 'Uploaders':
                    result.uploaders.addAll(
                        readValueList(it)
                    )
                    break

            // optional
                case 'Dm-Upload-Allowed':
                    result.dmUploadAllowed = (it.value == 'yes')
                    break

            // optional
                case 'Homepage':
                    result.homepage = it.value
                    break

            // optional
                case 'Vcs-Browser':
                case 'Vcs-Arch':
                case 'Vcs-Bzr':
                case 'Vcs-Cvs':
                case 'Vcs-Darcs':
                case 'Vcs-Git':
                case 'Vcs-Hg':
                case 'Vcs-Mtn':
                case 'Vcs-Svn':
                    result.vcsAddresses.addAll(
                        readVcsAddress(it)
                    )
                    break

            // recommended
                case 'Standards-Version':
                    result.standardsVersion = it.value
                    break

            // optional
                case 'Build-Depends':
                case 'Build-Depends-Indep':
                case 'Build-Conflicts':
                case 'Build-Conflicts-Indep':
                    result.dependencies.addAll(
                        readSrcDependency(it)
                    )
                    break

            // recommended
                case 'Checksums-Sha1':
                    result.checksumsSha1.addAll(
                        readSrcFileList(it)
                    )
                    break

            // recommended
                case 'Checksums-Sha256':
                    result.checksumsSha256.addAll(
                        readSrcFileList(it)
                    )
                    break

            // mandatory
                case 'Files':
                    result.files.addAll(
                        readSrcFileList(it)
                    )
                    break

            // mandatory
                case 'Directory':
                    result.directory = it.value
                    break

            // optional
                case 'Priority':
                    result.priority = it.value
                    break

            // optional
                case 'Section':
                    result.section = it.value
                    break

                default:
                    result.userDefinedFields.put(it.name, it.value)
                    break
            }
        }
        return result
    }

    static List<VcsAddressEntry> readVcsAddress(Header header) {
        VcsAddressType type = VcsAddressType.byHeaderName(header.name)
        List<VcsAddressEntry> result = new ArrayList<VcsAddressEntry>()
        List<String> values = readValueList(header)
        values.each {
            result.add(
                new VcsAddressEntry(type, it)
            )
        }
        return result
    }

    static List<DependencyEntry> readSrcDependency(Header header) {
        DependencyType type = DependencyType.byHeaderName(header.name)
        List<DependencyEntry> result = new ArrayList<DependencyEntry>()
        List<String> values = readValueList(header)
        values.each {
            result.add(
                new DependencyEntry(type, it)
            )
        }
        return result
    }

    static List<FileEntry> readSrcFileList(Header header)
    {
        List<FileEntry> result = new ArrayList<FileEntry>()
        List<String> values = header.value.readLines()
        values.each {
            if (it)
            {
                result.add(
                    FileEntry.parse(it)
                )
            }
        }
        return result
    }

    static List<String> readValueList(Header header)
    {
        List<String> result = new ArrayList<String>()
        String value = header.value
        List<String> values
        if (value.findAll(SIMPLE_INTERNET_ADDRESS).size() != 0)
        {
            values = splitInternetAddressesValueList(value)
        }
        else if (value.contains('"') || value.contains('\''))
        {
            values = splitComplexValueList(value)
        }
        else
        {
            values = value.tokenize(',')
        }
        values.each {
            result.add(
                it.trim()
            )
        }
        return result
    }

    static List<String> splitInternetAddressesValueList(String value)
    {
        List<String> result = new ArrayList<String>()
        Matcher matches = value =~ SIMPLE_INTERNET_ADDRESS
        matches.each {
            result.add(
                (String) it[0]
            )
        }
        return result
    }

    static List<String> splitComplexValueList(String value)
    {
        String valueWithSafeTokens = value.replaceAll(VALUE_LIST_DELIMITER, '\u001F')
        List<String> result = valueWithSafeTokens.tokenize('\u001F')
        return result
    }

}
