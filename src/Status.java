public class Status {
    private String stat;
    public Status(String s)
    {
        super();
        this.stat = s;
    }

    public boolean equals(String c)
    {
        return this.stat.equals(c);
    }
    public String toString()
    {
        return this.stat;
    }
}