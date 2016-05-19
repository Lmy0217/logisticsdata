package logistics.data.net;

import java.util.List;

import logistics.data.bean.Data;
import logistics.data.util.ByteUtil;

public class DataTranslate {

	private List<Byte> byteList;
	private Data data;
	private boolean checkflag;
	private String errInfo;

	public DataTranslate(List<Byte> byteList) {
		this.byteList = byteList;
		checkflag = init();
		if(DataSocketService.test)
			print();
	}

	public boolean init() {

		if (length() < 53) {
			errInfo = "Length < 53 error!";
			return false;
		}

		if (!escape()) {
			errInfo = "Escape error!";
			return false;
		}

		if (length() != 53) {
			errInfo = "Length < 53 error!";
			return false;
		}

		if (!format()) {
			return false;
		}

		if (!check()) {
			errInfo = "Check error!";
			return false;
		}
		
		if(!translate()) {
			errInfo = "Translate error!";
			return false;
		}

		return true;
	}

	public int length() {
		return byteList.size();
	}

	public boolean escape() {

		boolean flag = false;
		for (int i = 0; i < length(); i++) {
			if (!flag && byteList.get(i) == 0x7D) {
				flag = true;
				byteList.remove(i);
				i--;
			} else if (flag) {
				if (byteList.get(i) == 0x01) {
					byteList.set(i, (byte) 0x7E);
					flag = false;
				} else if (byteList.get(i) == 0x02) {
					byteList.set(i, (byte) 0x7D);
					flag = false;
				}
				if (flag) {
					break;
				}
			}
		}

		return !flag;
	}

	public boolean format() {

		if (byteList.get(0) != (byte) 0x03) {
			errInfo = "format 0 error!";
			return false;
		}

		if (byteList.get(31) != (byte) 0x01) {
			errInfo = "format 31 error!";
			return false;
		}

		return true;
	}

	public boolean check() {

		byte checkByte = byteList.get(0);
		for (int i = 1; i < 52; i++) {
			checkByte ^= byteList.get(i);
		}

		return checkByte == byteList.get(52);
	}

	public boolean translate() {

		data = new Data();

		try {
			data.setLower(ByteUtil.byte2LowHexStr(ByteUtil.subByteList(
					byteList, 1, 20)));
			data.setX(""
					+ (double) ByteUtil.OxStringtoInt(ByteUtil
							.byte2HexStr(ByteUtil.subByteList(byteList, 36, 39)))
					/ 1000000);
			data.setY(""
					+ (double) ByteUtil.OxStringtoInt(ByteUtil
							.byte2HexStr(ByteUtil.subByteList(byteList, 40, 43)))
					/ 1000000);
			data.setT(ByteUtil.OxStringtoInt(ByteUtil
					.byte2HexStr(new byte[] { byteList.get(32) })) - 100);
			data.setKx((double) ByteUtil.OxStringtoInt(ByteUtil
					.byte2HexStr(new byte[] { byteList.get(33) })) / 10);
			data.setKy((double) ByteUtil.OxStringtoInt(ByteUtil
					.byte2HexStr(new byte[] { byteList.get(34) })) / 10);
			data.setKz((double) ByteUtil.OxStringtoInt(ByteUtil
					.byte2HexStr(new byte[] { byteList.get(35) })) / 10);
			data.setPower(ByteUtil.OxStringtoInt(ByteUtil
					.byte2HexStr(new byte[] { byteList.get(44) })));
			data.setAlarm(ByteUtil.OxStringtoInt(ByteUtil
					.byte2HexStr(new byte[] { byteList.get(45) })));
			data.setTime("20"
					+ ByteUtil.byte2HexStr(new byte[] { byteList.get(46) })
					+ "-"
					+ ByteUtil.byte2HexStr(new byte[] { byteList.get(47) })
					+ "-"
					+ ByteUtil.byte2HexStr(new byte[] { byteList.get(48) })
					+ " "
					+ ByteUtil.byte2HexStr(new byte[] { byteList.get(49) })
					+ ":"
					+ ByteUtil.byte2HexStr(new byte[] { byteList.get(50) })
					+ ":"
					+ ByteUtil.byte2HexStr(new byte[] { byteList.get(51) }));
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		
		return true;
	}

	public String getId() {
		return checkflag ? data.getLower() : null;
	}

	public Data toData() {
		return checkflag ? data : null;
	}

	public String getErrInfo() {
		return errInfo;
	}

	public void print() {
		System.out.println("checkflag " + checkflag);
		System.out.println("errInfo " + errInfo);
		System.out.print("Stream: ");
		for (Byte b : byteList) {
			ByteUtil.printHexString(new byte[] { b });
			System.out.print(" ");
		}
		System.out.println();
	}

}
