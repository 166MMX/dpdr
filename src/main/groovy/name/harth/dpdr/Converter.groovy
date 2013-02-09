package name.harth.dpdr

import name.harth.dpdr.io.control.ControlReader
import name.harth.dpdr.io.gexf.GexfWriter
import name.harth.dpdr.model.gexf.Node
import name.harth.dpdr.model.control.BasicDebSrcIdxParagraph

class Converter
{
    static void main(String[] args)
    {
        File inputFile = new File(args[0])
        File outputFile = new File(args[1])
        List<BasicDebSrcIdxParagraph> paragraphs = ControlReader.readSrcIdx(inputFile)
        List<Node> nodeList = Transformer.transform(paragraphs)
        GexfWriter.write(outputFile, nodeList)
    }
}
