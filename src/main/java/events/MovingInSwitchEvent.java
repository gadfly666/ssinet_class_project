package events;

import elements.EntranceBuffer;
import elements.ExitBuffer;
import network.Packet;

enum TypeE{
	E, E1, E2
}

public class MovingInSwitchEvent extends Event {
	public TypeE type = TypeE.E;
	//Event dai dien cho su kien loai (E): goi tin roi khoi ENB cua Switch de sang EXB

	public Packet p;
	public EntranceBuffer entranceBuffer;
	public ExitBuffer exitBuffer;

	public MovingInSwitchEvent(long startTime, long endTime, EntranceBuffer entranceBuffer, ExitBuffer exitBuffer) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.entranceBuffer = entranceBuffer;
		this.exitBuffer = exitBuffer;
	}

	@Override
	public void execute() {
		Packet p = entranceBuffer.allPackets[0];
		System.out.println("Moving in switch");
		if(exitBuffer.insertPacket(p)) {
			entranceBuffer.aSwitch.physicalLayer.getByNextNodeId(exitBuffer.nodeId)
					.ifPresent(
					link -> exitBuffer.insertEvents(
							new LeavingEXBEvent(link, exitBuffer)
					)
			);
		}
	}
}
