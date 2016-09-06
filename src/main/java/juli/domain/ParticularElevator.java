package juli.domain;


import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ParticularElevator extends BaseEntity {

    @Column
    private String typeId;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime time;

    /**
     *第一版本电梯控制板
     */
    @Column
    private int LEDa;

    @Column
    private boolean lamp1;

    @Column
    private boolean lamp2;

    @Column
    private boolean lamp3;

    @Column
    private boolean lamp4;

    @Column
    private boolean lamp5;

    @Column
    private boolean lamp6;

    @Column
    private boolean lamp7;

    @Column
    private boolean lamp8;

    @Column
    private boolean lamp9;

    @Column
    private boolean lamp10;

    @Column
    private boolean lamp11;

    @Column
    private boolean lamp12;

    @Column
    private boolean lamp13;

    @Column
    private boolean lamp14;

    @Column
    private boolean lamp15;

    @Column
    private boolean lamp16;

    @Column
    private boolean lamp17;

    @Column
    private boolean lamp18;

    @Column
    private boolean lamp19;

    @Column
    private boolean lamp20;

    @Column
    private boolean lamp21;

    /**
     *第二版本电梯控制板
     */
    @Column
    private int led1;

    @Column
    private int led2;

    @Column
    private int led3;

    @Column
    private int led4;

    @Column
    private int led5;

    @Column
    private int led6;

    /**
     *第三版本控制器电梯
     */
    @Column
    private int light1;

    @Column
    private int light2;

    @Column
    private int light3;


    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getLEDa() {
        return LEDa;
    }

    public void setLEDa(int LEDa) {
        this.LEDa = LEDa;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public boolean isLamp1() {
        return lamp1;
    }

    public void setLamp1(boolean lamp1) {
        this.lamp1 = lamp1;
    }

    public boolean isLamp2() {
        return lamp2;
    }

    public void setLamp2(boolean lamp2) {
        this.lamp2 = lamp2;
    }

    public boolean isLamp3() {
        return lamp3;
    }

    public void setLamp3(boolean lamp3) {
        this.lamp3 = lamp3;
    }

    public boolean isLamp4() {
        return lamp4;
    }

    public void setLamp4(boolean lamp4) {
        this.lamp4 = lamp4;
    }

    public boolean isLamp5() {
        return lamp5;
    }

    public void setLamp5(boolean lamp5) {
        this.lamp5 = lamp5;
    }

    public boolean isLamp6() {
        return lamp6;
    }

    public void setLamp6(boolean lamp6) {
        this.lamp6 = lamp6;
    }

    public boolean isLamp7() {
        return lamp7;
    }

    public void setLamp7(boolean lamp7) {
        this.lamp7 = lamp7;
    }

    public boolean isLamp8() {
        return lamp8;
    }

    public void setLamp8(boolean lamp8) {
        this.lamp8 = lamp8;
    }

    public boolean isLamp9() {
        return lamp9;
    }

    public void setLamp9(boolean lamp9) {
        this.lamp9 = lamp9;
    }

    public boolean isLamp10() {
        return lamp10;
    }

    public void setLamp10(boolean lamp10) {
        this.lamp10 = lamp10;
    }

    public boolean isLamp11() {
        return lamp11;
    }

    public void setLamp11(boolean lamp11) {
        this.lamp11 = lamp11;
    }

    public boolean isLamp12() {
        return lamp12;
    }

    public void setLamp12(boolean lamp12) {
        this.lamp12 = lamp12;
    }

    public boolean isLamp13() {
        return lamp13;
    }

    public void setLamp13(boolean lamp13) {
        this.lamp13 = lamp13;
    }

    public boolean isLamp14() {
        return lamp14;
    }

    public void setLamp14(boolean lamp14) {
        this.lamp14 = lamp14;
    }

    public boolean isLamp15() {
        return lamp15;
    }

    public void setLamp15(boolean lamp15) {
        this.lamp15 = lamp15;
    }

    public boolean isLamp16() {
        return lamp16;
    }

    public void setLamp16(boolean lamp16) {
        this.lamp16 = lamp16;
    }

    public boolean isLamp17() {
        return lamp17;
    }

    public void setLamp17(boolean lamp17) {
        this.lamp17 = lamp17;
    }

    public boolean isLamp18() {
        return lamp18;
    }

    public void setLamp18(boolean lamp18) {
        this.lamp18 = lamp18;
    }

    public boolean isLamp19() {
        return lamp19;
    }

    public void setLamp19(boolean lamp19) {
        this.lamp19 = lamp19;
    }

    public boolean isLamp20() {
        return lamp20;
    }

    public void setLamp20(boolean lamp20) {
        this.lamp20 = lamp20;
    }

    public boolean isLamp21() {
        return lamp21;
    }

    public void setLamp21(boolean lamp21) {
        this.lamp21 = lamp21;
    }

    public int getLed1() {
        return led1;
    }

    public void setLed1(int led1) {
        this.led1 = led1;
    }

    public int getLed2() {
        return led2;
    }

    public void setLed2(int led2) {
        this.led2 = led2;
    }

    public int getLed3() {
        return led3;
    }

    public void setLed3(int led3) {
        this.led3 = led3;
    }

    public int getLed4() {
        return led4;
    }

    public void setLed4(int led4) {
        this.led4 = led4;
    }

    public int getLed5() {
        return led5;
    }

    public void setLed5(int led5) {
        this.led5 = led5;
    }

    public int getLed6() {
        return led6;
    }

    public void setLed6(int led6) {
        this.led6 = led6;
    }

    public int getLight1() {
        return light1;
    }

    public void setLight1(int light1) {
        this.light1 = light1;
    }

    public int getLight2() {
        return light2;
    }

    public void setLight2(int light2) {
        this.light2 = light2;
    }

    public int getLight3() {
        return light3;
    }

    public void setLight3(int light3) {
        this.light3 = light3;
    }
}
