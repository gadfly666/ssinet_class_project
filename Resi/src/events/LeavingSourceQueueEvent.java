package events;

import network.host.SourceQueue;

enum TypeB
{
	B, B1, B2, B3, B4
}

public class LeavingSourceQueueEvent extends Event {
	public TypeB type = TypeB.B;
	//Event dai dien cho su kien loai (B): goi tin roi khoi Source Queue
	public void execute()
	{
		if(elem instanceof SourceQueue)
		{
			
		}
	}
}
