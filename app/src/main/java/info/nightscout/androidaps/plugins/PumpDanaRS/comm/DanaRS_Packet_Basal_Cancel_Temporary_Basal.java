package info.nightscout.androidaps.plugins.PumpDanaRS.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.Config;

import com.cozmo.danar.util.BleCommandUtil;

public class DanaRS_Packet_Basal_Cancel_Temporary_Basal extends DanaRS_Packet {
    private static Logger log = LoggerFactory.getLogger(DanaRS_Packet_Basal_Cancel_Temporary_Basal.class);

    public int error;

    public DanaRS_Packet_Basal_Cancel_Temporary_Basal() {
        super();
        opCode = BleCommandUtil.DANAR_PACKET__OPCODE_BASAL__CANCEL_TEMPORARY_BASAL;
        if (Config.logDanaMessageDetail) {
            log.debug("Canceling temp basal");
        }
    }

    @Override
    public void handleMessage(byte[] data) {
        error = byteArrayToInt(getBytes(data, DATA_START, 1));
        if (Config.logDanaMessageDetail) {
            log.debug("Result " + error);
        }
    }

    @Override
    public String getFriendlyName() {
        return "BASAL__CANCEL_TEMPORARY_BASAL";
    }

}
