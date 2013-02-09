package name.harth.dpdr.model.control

enum DependencyType
{
    BUILD_DEPENDS('Build-Depends'),
    BUILD_DEPENDS_INDEP('Build-Depends-Indep'),
    BUILD_CONFLICTS('Build-Conflicts'),
    BUILD_CONFLICTS_INDEP('Build-Conflicts-Indep')

    final String name

    private static final Map<String, DependencyType> lookup = new HashMap<String, DependencyType>()

    static
    {
        for (type in DependencyType)
        {
            lookup.put(type.name, (DependencyType) type)
        }
    }

    private DependencyType(String name)
    {
        this.name = name
    }

    static DependencyType byHeaderName(String name)
    {
        return lookup.get(name)
    }
}