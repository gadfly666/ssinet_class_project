package elements;

import states.exb.X00;

public class ExitBuffer extends LimitedBuffer {
	public ExitBuffer()
	{
		state = new X00();
	}
}
