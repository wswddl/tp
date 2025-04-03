package seedu.address.logic.parser;

public class Flag {
    private final String flag;

    public Flag(String prefix) {
        this.flag = prefix;
    }

    public String getPrefix() {
        return flag;
    }

    @Override
    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return flag == null ? 0 : flag.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Flag)) {
            return false;
        }

        Flag otherFlag = (Flag) other;
        return flag.equals(otherFlag.flag);
    }
}

