package name.harth.dpdr

import name.harth.dpdr.model.control.BasicDebSrcIdxParagraph
import name.harth.dpdr.model.gexf.MaintainerNode
import name.harth.dpdr.model.gexf.SrcPkgNode
import name.harth.dpdr.model.gexf.Node

class Transformer
{
    static List<Node> transform (List<BasicDebSrcIdxParagraph> paragraphs)
    {
        List<Node> result = new ArrayList<Node>()
        paragraphs.each {
            result.addAll(
                transform(it)
            )
        }
        return result
    }

    static List<Node> transform (BasicDebSrcIdxParagraph paragraph)
    {
        List<Node> result         = new ArrayList<Node>()

        SrcPkgNode pkgNode        = new SrcPkgNode()

        pkgNode.format            = paragraph.format
        pkgNode.dmUploadAllowed   = paragraph.dmUploadAllowed
        pkgNode.standardsVersion  = paragraph.standardsVersion

        pkgNode.checksumsSha1     = paragraph.checksumsSha1.size()   > 0
        pkgNode.checksumsSha256   = paragraph.checksumsSha256.size() > 0
        pkgNode.files             = paragraph.files.size()

        pkgNode.version           = paragraph.version
        pkgNode.section           = paragraph.section
        pkgNode.priority          = paragraph.priority
        pkgNode.architecture      = paragraph.architecture
        pkgNode.homepage          = paragraph.homepage

        pkgNode.name              = paragraph.name

        MaintainerNode maintainerNode = MaintainerNode.create(paragraph.maintainer)
        pkgNode.maintainer = maintainerNode
        result.add(maintainerNode)

        paragraph.uploaders.each {
            MaintainerNode uploaderNode = MaintainerNode.create(it)
            pkgNode.uploaders.add(uploaderNode)
            result.add(uploaderNode)
        }

        result.add(pkgNode)
        return result
    }

}