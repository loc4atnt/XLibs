package loc4atnt.xlibs.item;

public class ItemIdFormat {
	
	private int typeId;
	private byte data;
	
	/**
	 * ItemIdFormat is only for Version 1.10 = > 1.12
	 * @param typeId
	 * @param data
	 */
	public ItemIdFormat(int typeId, byte data) {
		this.typeId=typeId;
		this.data=data;
	}
	
	public static ItemIdFormat getItemIdFormat(String s) {
		int index=s.indexOf(":");
		if(index!=-1) {
			String typeIdString=s.substring(0, index);
			String dataString=s.substring(index+1);
			int tId;
			byte dt;
			try {
				tId=Integer.parseInt(typeIdString);
				dt=Byte.parseByte(dataString);
				return new ItemIdFormat(tId, dt);
			}catch(NumberFormatException e) {
				return null;
			}
		}else {
			try {
				int tId=Integer.parseInt(s);
				return new ItemIdFormat(tId, (byte) 0);
			}catch(NumberFormatException e) {
				return null;
			}
		}
	}
	
	public String getStringFormat() {
		String temp=String.valueOf(typeId);
		if(data!=0) {
			temp=temp+":"+String.valueOf(data);
		}
		return temp;
	}
	
	public int getTypeId() {
		return this.typeId;
	}
	
	public byte getData() {
		return this.data;
	}
}
