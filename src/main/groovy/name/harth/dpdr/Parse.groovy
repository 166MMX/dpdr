package name.harth.dpdr

import name.harth.dpdr.model.file.DebianBinaryPackagesIndicesFile
import name.harth.dpdr.model.file.DebianSourceIndicesFile
import name.harth.dpdr.model.paragraph.DebianBinaryPackagesIndicesParagraph
import name.harth.dpdr.model.paragraph.DebianSourceIndicesParagraph
import org.apache.commons.compress.compressors.CompressorStreamFactory
import org.apache.commons.compress.compressors.bzip2.BZip2Utils
import org.apache.commons.compress.compressors.gzip.GzipUtils
import org.apache.commons.compress.compressors.xz.XZUtils
import org.slf4j.LoggerFactory

import javax.mail.internet.InternetHeaders

class Parse {

    private static final logger = LoggerFactory.getLogger(Parse.class)

    static parse(File file)
    {
        file.eachLine {

        }
        InputStream inputStream = file.newInputStream()
        String filename = file.name

        CompressorStreamFactory compressorStreamFactory = new CompressorStreamFactory()
        InputStream compressorInputStream = inputStream
        String uncompressedFilename = filename

        while (BZip2Utils.isCompressedFilename(uncompressedFilename) || GzipUtils.isCompressedFilename(uncompressedFilename) || XZUtils.isCompressedFilename(uncompressedFilename))
        {
            if (BZip2Utils.isCompressedFilename(uncompressedFilename))
            {
                uncompressedFilename = BZip2Utils.getUncompressedFilename(uncompressedFilename)
                compressorInputStream = compressorStreamFactory.createCompressorInputStream(CompressorStreamFactory.BZIP2, compressorInputStream)
            }
            else if (GzipUtils.isCompressedFilename(uncompressedFilename))
            {
                uncompressedFilename = GzipUtils.getUncompressedFilename(uncompressedFilename)
                compressorInputStream = compressorStreamFactory.createCompressorInputStream(CompressorStreamFactory.GZIP, compressorInputStream)
            }
            else if (XZUtils.isCompressedFilename(uncompressedFilename))
            {
                uncompressedFilename = XZUtils.getUncompressedFilename(uncompressedFilename)
                compressorInputStream = compressorStreamFactory.createCompressorInputStream(CompressorStreamFactory.XZ, compressorInputStream)
            }
        }

        if (uncompressedFilename == 'Packages')
        {
            parseBinaryIndex(compressorInputStream)
        }
        else if (uncompressedFilename == 'Sources')
        {
            parseSourceIndex(compressorInputStream)
        }
        else
        {
            throw new IllegalArgumentException('Unsupported file')
        }

    }

    private static DebianBinaryPackagesIndicesFile parseBinaryIndex(InputStream inputStream)
    {
        DebianBinaryPackagesIndicesFile debianBinaryPackagesIndicesFile = new DebianBinaryPackagesIndicesFile()
        while (true)
        {
            try
            {
                inputStream.available()
                InternetHeaders internetHeaders = new InternetHeaders(inputStream)
                Enumeration headers = internetHeaders.allHeaders
                DebianBinaryPackagesIndicesParagraph paragraph = new DebianBinaryPackagesIndicesParagraph(headers)
                debianBinaryPackagesIndicesFile.paragraphs.push(paragraph)
            }
            catch (IOException ex)
            {
                logger.debug('Possibly reached EOF', ex)
                break
            }
        }
        return debianBinaryPackagesIndicesFile
    }

    private static DebianSourceIndicesFile parseSourceIndex(InputStream inputStream)
    {
        DebianSourceIndicesFile debianSourceIndicesFile = new DebianSourceIndicesFile()
        while (true)
        {
            try
            {
                inputStream.available()
                InternetHeaders internetHeaders = new InternetHeaders(inputStream)
                Enumeration headers = internetHeaders.allHeaders
                DebianSourceIndicesParagraph paragraph = new DebianSourceIndicesParagraph(headers)
                debianSourceIndicesFile.paragraphs.push(paragraph)
            }
            catch (IOException ex)
            {
                logger.debug('Possibly reached EOF', ex)
                break
            }
        }
        return debianSourceIndicesFile
    }

}
