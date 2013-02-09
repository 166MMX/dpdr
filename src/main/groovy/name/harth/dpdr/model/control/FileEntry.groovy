package name.harth.dpdr.model.control

class FileEntry implements Serializable
{
    private static final long serialVersionUID = -1041327635167312836L

    String  checksum
    int     size
    String  fileName

    FileEntry()
    {

    }

    FileEntry(String checksum, int size, String fileName)
    {
        this.checksum = checksum
        this.size = size
        this.fileName = fileName
    }

    static FileEntry parse (String checksum, String size, String fileName)
    {
        new FileEntry(
            checksum,
            Integer.parseInt(size),
            fileName
        )
    }

    static FileEntry parse (String line)
    {
        List<String> tokens = line.tokenize()
        parse(
            tokens[0],
            tokens[1],
            tokens[2]
        )
    }

}