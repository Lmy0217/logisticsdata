package logistics.data.bean;

/**
 * 碎片类, 对应数据库碎片表, 保存不完整的上传数据
 * 
 * @author myluo(lmy0217@126.com)
 * @version 1605
 */
public class Fragment {

	/** 碎片ID */
	private String id;

	/** 碎片(不完整数据) */
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
