package name.harth.dpdr.model.gexf

class Node
{
    // Label
    // DPS:  Source (mandatory)
    // DPB:  Package (mandatory)
    // RFS:  The "Source" field is renamed to "Package"
    String name

    int id

    private static int count = 0

    Node()
    {
        id = count++
    }
}
