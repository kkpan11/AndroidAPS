package info.nightscout.androidaps.plugins.PumpDanaRS.comm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;
import info.nightscout.androidaps.plugins.PumpDanaR.DanaRPump;

import com.cozmo.danar.util.BleCommandUtil;

public class DanaRS_Packet_Basal_Get_Basal_Rate extends DanaRS_Packet {
	private static Logger log = LoggerFactory.getLogger(DanaRS_Packet_Basal_Cancel_Temporary_Basal.class);


	public DanaRS_Packet_Basal_Get_Basal_Rate() {
		super();
		opCode = BleCommandUtil.DANAR_PACKET__OPCODE_BASAL__GET_BASAL_RATE;
		if (Config.logDanaMessageDetail) {
			log.debug("Requesting basal rates");
		}
	}

	@Override
	public void handleMessage(byte[] data) {
		DanaRPump pump = DanaRPump.getInstance();

		int dataIndex = DATA_START;
		int dataSize = 2;
		pump.maxBasal = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

		dataIndex += dataSize;
		dataSize = 1;
		pump.basalStep = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;

		if (pump.pumpProfiles == null) pump.pumpProfiles = new double[4][];
		pump.pumpProfiles[pump.activeProfile] = new double[24];
		for (int i = 0, size = 24; i < size; i++) {
			dataIndex += dataSize;
			dataSize = 2;
			pump.pumpProfiles[pump.activeProfile][i] = byteArrayToInt(getBytes(data, dataIndex, dataSize)) / 100d;
		}
		if (Config.logDanaMessageDetail) {
			log.debug("Max basal: " + pump.maxBasal + " U");
			log.debug("Basal step: " + pump.basalStep + " U");
			for (int index = 0; index < 24; index++)
				log.debug("Basal " + String.format("%02d", index) + "h: " + pump.pumpProfiles[pump.activeProfile][index]);
		}
	}

	@Override
	public String getFriendlyName() {
		return "BASAL__GET_BASAL_RATE";
	}
}
