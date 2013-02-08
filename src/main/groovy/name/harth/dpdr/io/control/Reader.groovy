package name.harth.dpdr.io.control

import name.harth.dpdr.model.control.file.DebianSourceIndicesFile
import name.harth.dpdr.model.control.paragraph.BasicDebSrcIdxParagraph

import static name.harth.dpdr.model.control.paragraph.BasicDebSrcIdxParagraph.VcsAddressType
import static name.harth.dpdr.model.control.paragraph.BasicDebSrcIdxParagraph.DependencyType
import static name.harth.dpdr.model.control.paragraph.BasicDebSrcIdxParagraph.VcsAddressEntry
import static name.harth.dpdr.model.control.paragraph.BasicDebSrcIdxParagraph.DependencyEntry
import static name.harth.dpdr.model.control.paragraph.BasicDebSrcIdxParagraph.FileEntry

import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.apache.commons.compress.compressors.bzip2.BZip2Utils
import org.apache.commons.compress.compressors.gzip.GzipUtils
import org.apache.commons.compress.compressors.xz.XZUtils
import org.slf4j.LoggerFactory

import javax.mail.Header
import javax.mail.internet.InternetHeaders

class Reader
{
    private static final logger = LoggerFactory.getLogger(Reader.class)

    DebianSourceIndicesFile read (File file)
    {
        InputStream inputStream = new FileInputStream(file)
        inputStream = asd(inputStream, file.name)
        DebianSourceIndicesFile result = readSourceIndex(inputStream)
        return result
    }

    static InputStream asd (InputStream inputStream, String fileName)
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

    private static DebianSourceIndicesFile readSourceIndex(InputStream inputStream)
    {
        DebianSourceIndicesFile result = new DebianSourceIndicesFile()
        result.paragraphs = new ArrayList<BasicDebSrcIdxParagraph>()
        try
        {
            while (inputStream.available() > 0)
            {
                    InternetHeaders internetHeaders = new InternetHeaders(inputStream)
                    Enumeration<Header> headers = internetHeaders.allHeaders
//                if (!headers.hasMoreElements())
//                {
//                    break
//                }
                    BasicDebSrcIdxParagraph paragraph = readSourceIndexParagraph(headers)
                    result.paragraphs.push(paragraph)
                }
            }
        catch (IOException ex)
        {
            logger.debug('Possibly reached EOF', ex)
        }
        return result
    }

    private static BasicDebSrcIdxParagraph readSourceIndexParagraph (Enumeration<Header> headers)
    {
        BasicDebSrcIdxParagraph result = new BasicDebSrcIdxParagraph()
        result.vcsAddresses = new ArrayList<VcsAddressEntry>()
        result.dependencies = new ArrayList<DependencyEntry>()
        result.userDefinedFields = new HashMap<String, String>()
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
                    result.binary = readSetValue(it)
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
                    result.uploaders = readSetValue(it)
                    break

            // optional
                case 'Dm-Upload-Allowed':
                    result.dmUploadAllowed = it.value == 'yes'
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
                    VcsAddressType type = VcsAddressType.byName(it.name)
                    it.value.tokenize(',').each {
                        VcsAddressEntry entry = new VcsAddressEntry()
                        entry.type = type
                        entry.address = it.trim()
                        result.vcsAddresses.add(entry)
                    }
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
                    DependencyType type = DependencyType.byName(it.name)
                    it.value.tokenize(',').each {
                        DependencyEntry entry = new DependencyEntry()
                        entry.type = type
                        entry.dependency = it.trim()
                        result.dependencies.add(entry)
                    }
                    break

            // recommended
                case 'Checksums-Sha1':
                    result.checksumsSha1 = readFileEntryValues(it)
                    break

            // recommended
                case 'Checksums-Sha256':
                    result.checksumsSha256 = readFileEntryValues(it)
                    break

            // mandatory
                case 'Files':
                    result.files = readFileEntryValues(it)
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

    static Set<FileEntry> readFileEntryValues(Header header)
    {
        Set<FileEntry> result = new HashSet<FileEntry>()
        header.value.eachLine {
            if (it.trim().length() == 0)
            {
                return
            }
            List<String> tokens = it.tokenize()
            FileEntry entry = new FileEntry()
            entry.checksum = tokens[0]
            entry.size = Integer.parseInt(tokens[1])
            entry.fileName = tokens[2]
            result.add(entry)
        }
        return result
    }

    static Set<String> readSetValue(Header header)
    {
        Set<String> result = new HashSet<String>();
        header.value.tokenize(',').each {
            result.add(it.trim())
        }
        return result
    }
}
