package com.emi.mongo.domain;

import com.emi.mongo.common.enums.DomainType;
import com.emi.mongo.common.exception.CommonException;
import com.emi.mongo.common.exception.RecordStatusException;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by emi on 2017/6/7.
 * @PersistenceConstructor
 * @Transient
 * @CompoundIndexes({
     @CompoundIndex(name = "birthday_idx", def = "{'name': 1, 'birthday': -1}")
     })
 */
public class Member extends BaseDomain {

    @Indexed(unique = true)
    private String phone;

    private String realName;

    private String idCard;

    private int sex;//1:男  2:女

    private String portrait;

    private int deposit;//押金

    private int balance;//余额

    private int freezeAmounts;//冻结金额

    private int validState = 4;//1:待验证  2:通过  3:未通过  4:新手

    public Member() {
    }

    public Member(String phone) {
        this.phone = phone;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getValidState() {
        return validState;
    }

    public void setValidState(int validState) {
        this.validState = validState;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getFreezeAmounts() {
        return freezeAmounts;
    }

    public void setFreezeAmounts(int freezeAmounts) {
        this.freezeAmounts = freezeAmounts;
    }

    public void verifyIdentity(String realName, String idCard, int sex) {
        this.realName = realName;
        this.idCard = idCard;
        this.sex = sex;
        this.validState = 1;
    }

    public void checkState(int... states) {
        for (int s : states) {
            if (this.validState == s) {
                return;
            }
        }
        throw new RecordStatusException(validState, DomainType.Member, getId(), states);
    }

    public void checkDeposit() {
        if (this.deposit == 0) {
            throw new CommonException("请先充值押金");
        }
        if (this.deposit < 50) {
            throw new CommonException("您的押金过低，请先续充押金");
        }
    }

    public void freezeBalance(int amounts) {
        if (this.balance < amounts) {
            throw new CommonException("您的余额低于订单金额，请充值后再下单");
        }
        this.balance = balance - amounts;
        this.freezeAmounts = this.freezeAmounts + amounts;
    }

    public void cancelOrder(int amounts) {
        this.freezeAmounts = freezeAmounts - amounts;
        this.balance = balance + amounts;
    }

    public void cancelOrderWithFine(int cancelOutPrice, int amounts) {
        cancelOrder(amounts);
        this.balance = balance - cancelOutPrice;
    }

    public void payOrder(int amounts) {
        this.freezeAmounts = this.freezeAmounts - amounts;
    }

    public void payOutTimeGo(int goOutPrice) {
        if (this.deposit - goOutPrice < 0) {
            throw new CommonException("用户押金不足抵扣罚款，请现金支付罚款后再出库");
        }
        this.deposit = this.deposit - goOutPrice;
    }

    public void recharge(int amounts, boolean deposit) {
        if (deposit) {
            this.deposit = this.deposit + amounts;
        } else {
            this.balance = this.balance + amounts;
        }
    }

    public void addBalance(int addAmounts) {
        this.balance = this.balance + addAmounts;
    }
}
