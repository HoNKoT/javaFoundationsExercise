package honkot.java.foundation.training.sub;

/**
 * Created by hhonda on 2016-11-21.
 */
public class SuperHuman extends Human {
    private String nickName;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public void printAllInfo() {
        super.printAllInfo();
        StringBuffer buf = new StringBuffer();
        buf.append("nickname : ");
        buf.append(nickName);
        System.out.println(buf.toString());
    }
}
