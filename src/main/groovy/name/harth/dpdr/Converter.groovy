package name.harth.dpdr

import name.harth.dpdr.io.control.Reader
import name.harth.dpdr.model.control.file.DebianSourceIndicesFile

class Converter
{
    static void main(String[] args)
    {
        File inputFile = new File(args[0])
        Reader reader = new Reader()
        DebianSourceIndicesFile indicesFile = reader.read(inputFile)
        true
    }
}
