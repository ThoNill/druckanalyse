package thomas.nill.druckanalyse;

public class FeldVerbindung {
    private String inName;
    private int inIndex;
    private String outName;
    private int outIndex;

    public FeldVerbindung(String inName, int inIndex, String outName,
            int outIndex) {
        super();
        this.inName = inName;
        this.inIndex = inIndex;
        this.outName = outName;
        this.outIndex = outIndex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + inIndex;
        result = prime * result + ((inName == null) ? 0 : inName.hashCode());
        result = prime * result + outIndex;
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
        FeldVerbindung other = (FeldVerbindung) obj;
        if (inIndex != other.inIndex)
            return false;
        if (inName == null) {
            if (other.inName != null)
                return false;
        } else if (!inName.equals(other.inName))
            return false;
        if (outIndex != other.outIndex)
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

    public int getInIndex() {
        return inIndex;
    }

    public String getOutName() {
        return outName;
    }

    public int getOutIndex() {
        return outIndex;
    }

    @Override
    public String toString() {
        return "FeldVerbindung [inName=" + inName + ", inIndex=" + inIndex
                + ", outName=" + outName + ", outIndex=" + outIndex + "]";
    }

}
