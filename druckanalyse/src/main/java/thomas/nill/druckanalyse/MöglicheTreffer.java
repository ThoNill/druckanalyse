package thomas.nill.druckanalyse;

public class MöglicheTreffer {
    private String inName;
    private String outName;

    public MöglicheTreffer(String inName, String outName) {
        super();
        this.inName = inName;
        this.outName = outName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((inName == null) ? 0 : inName.hashCode());
        result = prime * result + ((outName == null) ? 0 : outName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MöglicheTreffer other = (MöglicheTreffer) obj;
        if (inName == null) {
            if (other.inName != null)
                return false;
        } else if (!inName.equals(other.inName))
            return false;
        if (outName == null) {
            if (other.outName != null)
                return false;
        } else if (!outName.equals(other.outName))
            return false;
        return true;
    }

    public String getInName() {
        return inName;
    }

    public String getOutName() {
        return outName;
    }

    @Override
    public String toString() {
        return "MöglicheTreffer [inName=" + inName + ", outName=" + outName
                + "]";
    }

}
