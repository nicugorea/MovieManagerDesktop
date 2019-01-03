package mmd.common.types;

public class Tuple<First,Second>
{

    public Tuple(final First first, final Second second)
    {
	this.setFirst(first);
	this.setSecond(second);
    }

    public First getFirst()
    {
	return first;
    }
    public void setFirst(First first)
    {
	this.first = first;
    }

    public Second getSecond()
    {
	return second;
    }

    public void setSecond(Second second)
    {
	this.second = second;
    }

    private First first = null;
    private Second second = null;
}
