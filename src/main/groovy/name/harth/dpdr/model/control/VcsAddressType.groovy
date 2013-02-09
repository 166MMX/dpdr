package name.harth.dpdr.model.control

enum VcsAddressType
{
    BROWSER('Vcs-Browser'),
    ARCH('Vcs-Arch'),
    BZR('Vcs-Bzr'),
    CVS('Vcs-Cvs'),
    DARCS('Vcs-Darcs'),
    GIT('Vcs-Git'),
    HG('Vcs-Hg'),
    MTN('Vcs-Mtn'),
    SVN('Vcs-Svn')

    final String name

    private static final Map<String, VcsAddressType> lookup = new HashMap<String, VcsAddressType>()

    static
    {
        for (type in VcsAddressType)
        {
            lookup.put(type.name, (VcsAddressType) type)
        }
    }

    private VcsAddressType(String name)
    {
        this.name = name
    }

    static VcsAddressType byHeaderName(String name)
    {
        lookup.get(name)
    }
}