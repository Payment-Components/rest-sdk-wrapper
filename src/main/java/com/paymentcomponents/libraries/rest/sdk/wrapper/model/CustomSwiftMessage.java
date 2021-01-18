package com.paymentcomponents.libraries.rest.sdk.wrapper.model;

import gr.datamation.swift.common.SwiftMessage;
import gr.datamation.swift.common.Tag;

import java.util.Vector;

public class CustomSwiftMessage {

    SwiftMessage swiftMessage;

    public CustomSwiftMessage(SwiftMessage swiftMessage) {
        this.swiftMessage = swiftMessage;
    }

    public SwiftMessage cloneMessage() {
        return swiftMessage.cloneMessage();
    }

    public void clear() {
        swiftMessage.clear();
    }

    public void displayMessage() {
        swiftMessage.displayMessage();
    }

    public String getFormattedMessage() {
        return swiftMessage.getFormattedMessage();
    }

    public String getAck108() {
        return swiftMessage.getAck108();
    }

    public void setAck108(String newAck108) {
        swiftMessage.setAck108(newAck108);
    }

    public String getAck177() {
        return swiftMessage.getAck177();
    }

    public void setAck177(String newAck177) {
        swiftMessage.setAck177(newAck177);
    }

    public String getAck405() {
        return swiftMessage.getAck405();
    }

    public void setAck405(String newAck405) {
        swiftMessage.setAck405(newAck405);
    }

    public String getAck451() {
        return swiftMessage.getAck451();
    }

    public void setAck451(String newAck451) {
        swiftMessage.setAck451(newAck451);
    }

    public String getAckApplid() {
        return swiftMessage.getAckApplid();
    }

    public void setAckApplid(String newAckApplid) {
        swiftMessage.setAckApplid(newAckApplid);
    }

    public String getAckLTaddrBlk1() {
        return swiftMessage.getAckLTaddrBlk1();
    }

    public void setAckLTaddrBlk1(String newAckLTaddrBlk1) {
        swiftMessage.setAckLTaddrBlk1(newAckLTaddrBlk1);
    }

    public String getAckOsn() {
        return swiftMessage.getAckOsn();
    }

    public void setAckOsn(String newAckOsn) {
        swiftMessage.setAckOsn(newAckOsn);
    }

    public String getAckServid() {
        return swiftMessage.getAckServid();
    }

    public void setAckServid(String newAckServid) {
        swiftMessage.setAckServid(newAckServid);
    }

    public String getAckSesno() {
        return swiftMessage.getAckSesno();
    }

    public void setAckSesno(String newAckSesno) {
        swiftMessage.setAckSesno(newAckSesno);
    }

    public String getArgApplid() {
        return swiftMessage.getArgApplid();
    }

    public void setArgApplid(String newArgApplid) {
        swiftMessage.setArgApplid(newArgApplid);
    }

    public String getArgDelMon() {
        return swiftMessage.getArgDelMon();
    }

    public void setArgDelMon(String newArgDelMon) {
        swiftMessage.setArgDelMon(newArgDelMon);
    }

    public String getArgIndate() {
        return swiftMessage.getArgIndate();
    }

    public void setArgIndate(String newArgIndate) {
        swiftMessage.setArgIndate(newArgIndate);
    }

    public String getArgInoutind() {
        return swiftMessage.getArgInoutind();
    }

    public void setArgInoutind(String newArgInoutind) {
        swiftMessage.setArgInoutind(newArgInoutind);
    }

    public String getArgIntime() {
        return swiftMessage.getArgIntime();
    }

    public void setArgIntime(String newArgIntime) {
        swiftMessage.setArgIntime(newArgIntime);
    }

    public String getArgIsn() {
        return swiftMessage.getArgIsn();
    }

    public void setArgIsn(String newArgIsn) {
        swiftMessage.setArgIsn(newArgIsn);
    }

    public String getArgLTaddrBlk1() {
        return swiftMessage.getArgLTaddrBlk1();
    }

    public void setArgLTaddrBlk1(String newArgLTaddrBlk1) {
        swiftMessage.setArgLTaddrBlk1(newArgLTaddrBlk1);
    }

    public String getArgLTaddrBlk2() {
        return swiftMessage.getArgLTaddrBlk2();
    }

    public void setArgLTaddrBlk2(String newArgLTaddrBlk2) {
        swiftMessage.setArgLTaddrBlk2(newArgLTaddrBlk2);
    }

    public String getArgMsgprior() {
        return swiftMessage.getArgMsgprior();
    }

    public void setArgMsgprior(String newArgMsgprior) {
        swiftMessage.setArgMsgprior(newArgMsgprior);
    }

    public String getArgMsgtype() {
        return swiftMessage.getArgMsgtype();
    }

    public void setArgMsgtype(String newArgMsgtype) {
        swiftMessage.setArgMsgtype(newArgMsgtype);
    }

    public String getArgObsPer() {
        return swiftMessage.getArgObsPer();
    }

    public void setArgObsPer(String newArgObsPer) {
        swiftMessage.setArgObsPer(newArgObsPer);
    }

    public String getArgOsn() {
        return swiftMessage.getArgOsn();
    }

    public void setArgOsn(String newArgOsn) {
        swiftMessage.setArgOsn(newArgOsn);
    }

    public String getArgOutdate() {
        return swiftMessage.getArgOutdate();
    }

    public void setArgOutdate(String newArgOutdate) {
        swiftMessage.setArgOutdate(newArgOutdate);
    }

    public String getArgOuttime() {
        return swiftMessage.getArgOuttime();
    }

    public void setArgOuttime(String newArgOuttime) {
        swiftMessage.setArgOuttime(newArgOuttime);
    }

    public String getArgServid() {
        return swiftMessage.getArgServid();
    }

    public void setArgServid(String newArgServid) {
        swiftMessage.setArgServid(newArgServid);
    }

    public String getArgSesno() {
        return swiftMessage.getArgSesno();
    }

    public void setArgSesno(String newArgSesno) {
        swiftMessage.setArgSesno(newArgSesno);
    }

    public String getArgSsesno() {
        return swiftMessage.getArgSsesno();
    }

    public void setArgSsesno(String newArgSsesno) {
        swiftMessage.setArgSsesno(newArgSsesno);
    }

    public String getUniqueEndToEndTrxRef() {
        return swiftMessage.getUniqueEndToEndTrxRef();
    }

    public void setUniqueEndToEndTrxRef(String newUniqueEndToEndTrxRef) {
        swiftMessage.setUniqueEndToEndTrxRef(newUniqueEndToEndTrxRef);
    }

    public String getServiceTypeIdentifier() {
        return swiftMessage.getServiceTypeIdentifier();
    }

    public void setServiceTypeIdentifier(String newServiceTypeIdentifier) {
        swiftMessage.setServiceTypeIdentifier(newServiceTypeIdentifier);
    }

    public String getPaymentControlsInformation() {
        return swiftMessage.getPaymentControlsInformation();
    }

    public void setPaymentControlsInformation(String newPaymentControlsInformation) {
        swiftMessage.setPaymentControlsInformation(newPaymentControlsInformation);
    }

    public Vector getBlock1() {
        return swiftMessage.getBlock1();
    }

    public void setBlock1(Vector newBlock1) {
        swiftMessage.setBlock1(newBlock1);
    }

    public Vector getBlock2() {
        return swiftMessage.getBlock2();
    }

    public void setBlock2(Vector newBlock2) {
        swiftMessage.setBlock2(newBlock2);
    }

    public Vector getBlock3() {
        return swiftMessage.getBlock3();
    }

    public void setBlock3(Vector newBlock3) {
        swiftMessage.setBlock3(newBlock3);
    }

    public Vector getBlock4() {
        return swiftMessage.getBlock4();
    }

    public void setBlock4(Vector newBlock4) {
        swiftMessage.setBlock4(newBlock4);
    }

    public Vector getBlock5() {
        return swiftMessage.getBlock5();
    }

    public void setBlock5(Vector newBlock5) {
        swiftMessage.setBlock5(newBlock5);
    }

    public Vector getMessageAsVector() {
        return null;
    }

    public int getNumbertOfTagInstances(String name) {
        return swiftMessage.getNumbertOfTagInstances(name);
    }

    public Tag getTag(String name) {
        return swiftMessage.getTag(name);
    }

    public Tag getTagFromVector(Vector inVec, String tagName) {
        return swiftMessage.getTagFromVector(inVec, tagName);
    }

    public Tag getTag(String name, int index) {
        return swiftMessage.getTag(name, index);
    }

    public boolean isblockEmpty(int index) throws Exception {
        return swiftMessage.isblockEmpty(index);
    }

    public boolean isEnvelope() {
        return swiftMessage.isEnvelope();
    }

    public void setEnvelope(boolean envelope) {
        swiftMessage.setEnvelope(envelope);
    }
}
