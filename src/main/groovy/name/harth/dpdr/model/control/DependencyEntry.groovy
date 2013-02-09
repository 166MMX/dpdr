package name.harth.dpdr.model.control

class DependencyEntry implements Serializable
{
    private static final long serialVersionUID = 513062054266975194L

    DependencyType type
    String dependency

    DependencyEntry()
    {

    }

    DependencyEntry(String type, String dependency)
    {
        this.type = DependencyType.byHeaderName(type)
        this.dependency = dependency
    }

    DependencyEntry(DependencyType type, String dependency)
    {
        this.type = type
        this.dependency = dependency
    }

}