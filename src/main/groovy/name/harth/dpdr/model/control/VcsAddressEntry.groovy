package name.harth.dpdr.model.control

class VcsAddressEntry implements Serializable
{
    private static final long serialVersionUID = 4207357272037800240L

    VcsAddressType type
    String address

    VcsAddressEntry()
    {

    }

    VcsAddressEntry(String type, String address)
    {
        this.type = VcsAddressType.byHeaderName(type)
        this.address = address
    }

    VcsAddressEntry(VcsAddressType type, String address)
    {
        this.type = type
        this.address = address
    }

}
