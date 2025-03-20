package com.paymentcomponents.libraries.rest.sdk.wrapper;

import com.paymentcomponents.libraries.rest.sdk.wrapper.utils.Utils;

public class TestConstants {

    public static final String VALID_MT_101 = "{1:F01COPZBEB0AXXX0377002843}{2:O1011519110804LRLRXXXX4A1100009044661108041720N}{3:{108:MT101 006 OF 020}{433:/AOK/NO HIT DETECTED     }}{4:\n" +
            ":20:00043\n" +
            ":28D:1/1\n" +
            ":50F:/409074-293-45/786\n" +
            "1/George Philips\n" +
            "2/High Street 1\n" +
            "3/GB/London\n" +
            ":30:011231\n" +
            ":21:PQR-27ZZ-01\n" +
            ":32B:USD2564,50\n" +
            ":57D:/C/Clementine Nuggets-1842-Y\n" +
            "MynR49R RailRoad Trust\n" +
            "Cloudsboro ARTUI\n" +
            ":59F:1/Beneficiary Name-1234567891234123\n" +
            "2/QWERT\n" +
            "3/US/Beneficiary Address Line 21\n" +
            "3/Beneficiary Name-1234567891234123\n" +
            ":71A:OUR\n" +
            "-}{5:{MAC:00000000}{CHK:19DA346889CC}{TNG:}}{S:{SAC:}{COP:P}}";

    public static final String VALID_SEPA_PACS_008 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08\">\n" +
            "    <FIToFICstmrCdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>SSSSSS200020190305ORD000011119</MsgId>\n" +
            "            <CreDtTm>2019-03-06T14:56:10</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <TtlIntrBkSttlmAmt Ccy=\"EUR\">228.71</TtlIntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2019-03-06</IntrBkSttlmDt>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys>\n" +
            "                    <Cd>REP</Cd>\n" +
            "                </ClrSys>\n" +
            "            </SttlmInf>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICB</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>BSCHESMMCT1201903054095263553</InstrId>\n" +
            "                <EndToEndId>NOT PROVIDED</EndToEndId>\n" +
            "                <TxId>00730100632BHGCRWC</TxId>\n" +
            "            </PmtId>\n" +
            "            <PmtTpInf>\n" +
            "                <SvcLvl>\n" +
            "                    <Cd>SEPA</Cd>\n" +
            "                </SvcLvl>\n" +
            "            </PmtTpInf>\n" +
            "            <IntrBkSttlmAmt Ccy=\"EUR\">228.71</IntrBkSttlmAmt>\n" +
            "            <ChrgBr>SLEV</ChrgBr>\n" +
            "            <Dbtr>\n" +
            "                <Nm>JOHN DOE</Nm>\n" +
            "                <PstlAdr>\n" +
            "                    <Ctry>ES</Ctry>\n" +
            "                    <AdrLine>ADDRESS 1</AdrLine>\n" +
            "                    <AdrLine>ADDRESS 2</AdrLine>\n" +
            "                </PstlAdr>\n" +
            "                <Id>\n" +
            "                    <PrvtId>\n" +
            "                        <DtAndPlcOfBirth>\n" +
            "                            <BirthDt>1982-02-12</BirthDt>\n" +
            "                            <PrvcOfBirth>TEST</PrvcOfBirth>\n" +
            "                            <CityOfBirth>Athens</CityOfBirth>\n" +
            "                            <CtryOfBirth>GR</CtryOfBirth>\n" +
            "                        </DtAndPlcOfBirth>\n" +
            "                        <Othr>\n" +
            "                            <Id>44912150G</Id>\n" +
            "                            <SchmeNm>\n" +
            "                                <Prtry>N</Prtry>\n" +
            "                            </SchmeNm>\n" +
            "                        </Othr>\n" +
            "                    </PrvtId>\n" +
            "                </Id>\n" +
            "            </Dbtr>\n" +
            "            <DbtrAcct>\n" +
            "                <Id>\n" +
            "                    <IBAN>ES5300730100503232323232</IBAN>\n" +
            "                </Id>\n" +
            "            </DbtrAcct>\n" +
            "            <DbtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICA</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </DbtrAgt>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICB</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <Cdtr>\n" +
            "                <Nm>Jane Doe</Nm>\n" +
            "                <PstlAdr>\n" +
            "                    <Ctry>GR</Ctry>\n" +
            "                    <AdrLine>AdressLine 1</AdrLine>\n" +
            "                    <AdrLine>AdressLine 2</AdrLine>\n" +
            "                </PstlAdr>\n" +
            "                <Id>\n" +
            "                    <PrvtId>\n" +
            "                        <DtAndPlcOfBirth>\n" +
            "                            <BirthDt>1982-02-12</BirthDt>\n" +
            "                            <PrvcOfBirth>TEST</PrvcOfBirth>\n" +
            "                            <CityOfBirth>Athens</CityOfBirth>\n" +
            "                            <CtryOfBirth>GR</CtryOfBirth>\n" +
            "                        </DtAndPlcOfBirth>\n" +
            "                        <Othr>\n" +
            "                            <Id>SOMEID 1</Id>\n" +
            "                            <SchmeNm>\n" +
            "                                <Prtry>PRTRY</Prtry>\n" +
            "                            </SchmeNm>\n" +
            "                            <Issr>ISSUER</Issr>\n" +
            "                        </Othr>\n" +
            "                    </PrvtId>\n" +
            "                </Id>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id>\n" +
            "                    <IBAN>ES1911110001063300445566</IBAN>\n" +
            "                </Id>\n" +
            "            </CdtrAcct>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FIToFICstmrCdtTrf>\n" +
            "</Document>\n";

    public static final String VALID_SEPA_EPC_PACS_002 = "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\">\n" +
            "    <FIToFIPmtStsRpt>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>SCTREJ200020190314300000000001</MsgId>\n" +
            "            <CreDtTm>2019-03-14T00:00:00</CreDtTm>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICA</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "        </GrpHdr>\n" +
            "        <OrgnlGrpInfAndSts>\n" +
            "            <OrgnlMsgId>SSSSSS111120190313090033395001</OrgnlMsgId>\n" +
            "            <OrgnlMsgNmId>pacs.008.001.02</OrgnlMsgNmId>\n" +
            "            <GrpSts>PART</GrpSts>\n" +
            "        </OrgnlGrpInfAndSts>\n" +
            "        <TxInfAndSts>\n" +
            "            <StsId>000R907330000001</StsId>\n" +
            "            <OrgnlInstrId>b18332f58ca64ffd87ea9777b9edfba1</OrgnlInstrId>\n" +
            "            <OrgnlEndToEndId>NOTPROVIDED</OrgnlEndToEndId>\n" +
            "            <OrgnlTxId>111T9072000000000000000000000000006</OrgnlTxId>\n" +
            "            <StsRsnInf>\n" +
            "                <Orgtr>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>TESTBICB</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </Orgtr>\n" +
            "                <Rsn>\n" +
            "                    <Cd>AC01</Cd>\n" +
            "                </Rsn>\n" +
            "            </StsRsnInf>\n" +
            "            <OrgnlTxRef>\n" +
            "                <IntrBkSttlmAmt Ccy=\"EUR\">9.95</IntrBkSttlmAmt>\n" +
            "                <Dbtr>\n" +
            "                    <Pty>\n" +
            "                        <Nm>Schneider</Nm>\n" +
            "                        <PstlAdr>\n" +
            "                            <StrtNm>Kuertman Strasse</StrtNm>\n" +
            "                            <BldgNb>45</BldgNb>\n" +
            "                            <PstCd>50475</PstCd>\n" +
            "                            <TwnNm>Koln</TwnNm>\n" +
            "                            <Ctry>DE</Ctry>\n" +
            "                        </PstlAdr>\n" +
            "                    </Pty>\n" +
            "                </Dbtr>\n" +
            "                <DbtrAcct>\n" +
            "                    <Id>\n" +
            "                        <IBAN>ES1011110001087939390799</IBAN>\n" +
            "                    </Id>\n" +
            "                </DbtrAcct>\n" +
            "                <DbtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICA</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </DbtrAgt>\n" +
            "                <CdtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICB</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </CdtrAgt>\n" +
            "                <Cdtr>\n" +
            "                    <Pty>\n" +
            "                        <Nm>Schneider</Nm>\n" +
            "                        <PstlAdr>\n" +
            "                            <StrtNm>Kuertman Strasse</StrtNm>\n" +
            "                            <BldgNb>45</BldgNb>\n" +
            "                            <PstCd>50475</PstCd>\n" +
            "                            <TwnNm>Koln</TwnNm>\n" +
            "                            <Ctry>DE</Ctry>\n" +
            "                        </PstlAdr>\n" +
            "                    </Pty>\n" +
            "                </Cdtr>\n" +
            "                <CdtrAcct>\n" +
            "                    <Id>\n" +
            "                        <IBAN>ES5410820934549505717622</IBAN>\n" +
            "                    </Id>\n" +
            "                </CdtrAcct>\n" +
            "            </OrgnlTxRef>\n" +
            "        </TxInfAndSts>\n" +
            "    </FIToFIPmtStsRpt>\n" +
            "</Document>";

    public static final String VALID_MX_PACS_009 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.10\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>BBBB/150928-FICT/JPY/430</MsgId>\n" +
            "            <CreDtTm>2015-09-28T16:00:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>INDA</SttlmMtd>\n" +
            "            </SttlmInf>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>BBBB/150928-FICT/JPY/430/1</InstrId>\n" +
            "                <EndToEndId>ABC/4562/2015-09-08</EndToEndId>\n" +
            "                <TxId>BBBB/150928-CCT/123/1</TxId>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"JPY\">10000000</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2015-09-29</IntrBkSttlmDt>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAGB2L</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <UndrlygCstmrCdtTrf>\n" +
            "                <InitgPty>\n" +
            "                    <Nm>ABC Corporation</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <StrtNm>Times Square</StrtNm>\n" +
            "                        <BldgNb>7</BldgNb>\n" +
            "                        <PstCd>NY 10036</PstCd>\n" +
            "                        <TwnNm>New York</TwnNm>\n" +
            "                        <Ctry>US</Ctry>\n" +
            "                    </PstlAdr>\n" +
            "                </InitgPty>\n" +
            "                <Dbtr>\n" +
            "                    <Nm>ABC Corporation</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <StrtNm>Times Square</StrtNm>\n" +
            "                        <BldgNb>7</BldgNb>\n" +
            "                        <PstCd>NY 10036</PstCd>\n" +
            "                        <TwnNm>New York</TwnNm>\n" +
            "                        <Ctry>US</Ctry>\n" +
            "                    </PstlAdr>\n" +
            "                </Dbtr>\n" +
            "                <DbtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>BBBBUS33</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </DbtrAgt>\n" +
            "                <CdtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>AAAAGB2L</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </CdtrAgt>\n" +
            "                <Cdtr>\n" +
            "                    <Nm>DEF Electronics</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <StrtNm>Mark Lane</StrtNm>\n" +
            "                        <BldgNb>55</BldgNb>\n" +
            "                        <PstCd>EC3RZNE</PstCd>\n" +
            "                        <TwnNm>London</TwnNm>\n" +
            "                        <Ctry>GB</Ctry>\n" +
            "                        <AdrLine>Corn Exchange 5th Floor</AdrLine>\n" +
            "                    </PstlAdr>\n" +
            "                </Cdtr>\n" +
            "                <CdtrAcct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>29683707994815</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </CdtrAcct>\n" +
            "                <RmtInf>\n" +
            "                    <Strd>\n" +
            "                        <RfrdDocInf>\n" +
            "                            <Tp>\n" +
            "                                <CdOrPrtry>\n" +
            "                                    <Cd>CINV</Cd>\n" +
            "                                </CdOrPrtry>\n" +
            "                            </Tp>\n" +
            "                            <Nb>4562</Nb>\n" +
            "                            <RltdDt>2015-09-08</RltdDt>\n" +
            "                        </RfrdDocInf>\n" +
            "                    </Strd>\n" +
            "                </RmtInf>\n" +
            "            </UndrlygCstmrCdtTrf>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>";

    public static final String VALID_CBPR_TRANSLATOR_MT_TO_MX_REQUEST = "{1:F01AAAABEBBAXXX0000000000}{2:I202CCCCUS33AXXXN}{3:{121:c8b66b47-2bd9-48fe-be90-93c2096f27d2}}{4:\n" +
            ":20:987\n" +
            ":21:090525/123COV\n" +
            ":13C:/SNDTIME/1249+0200\n" +
            ":32A:090527USD10500,00\n" +
            ":52A:BKAUATWW\n" +
            ":56A:TESTBICD\n" +
            ":57A:TESTBICE\n" +
            ":58A:TESTBICF\n" +
            ":72:/INS/CHASUS33\n" +
            "-}{5:{MAC:75D138E4}{CHK:DE1B0D71FA96}}";

    public static final String VALID_CBPR_TRANSLATOR_MT_TO_MX_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n" +
            "    <Fr>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>AAAABEBBXXX</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </Fr>\n" +
            "    <To>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>CCCCUS33XXX</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </To>\n" +
            "    <BizMsgIdr>987</BizMsgIdr>\n" +
            "    <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "    <BizSvc>swift.cbprplus.02</BizSvc>\n" +
            "    <CreDt>2021-06-02T11:05:42.864+03:00</CreDt>\n" +
            "    <Prty>NORM</Prty>\n" +
            "</AppHdr>\n" +
            "\n" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>987</MsgId>\n" +
            "            <CreDtTm>2021-06-02T11:05:42.887+03:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>INDA</SttlmMtd>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>987</InstrId>\n" +
            "                <EndToEndId>090525/123COV</EndToEndId>\n" +
            "                <UETR>c8b66b47-2bd9-48fe-be90-93c2096f27d2</UETR>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"USD\">10500</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2009-05-27</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <DbtDtTm>2009-05-27T12:49:00.000+02:00</DbtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <PrvsInstgAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CHASUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </PrvsInstgAgt1>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAABEBBXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCUS33XXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <IntrmyAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICD</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </IntrmyAgt1>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BKAUATWW</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICE</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICF</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>\n";

    public static final String VALID_CBPR_TRANSLATOR_MX_TO_MT_REQUEST = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n" +
            "    <Fr>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>AAAABEBBXXX</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </Fr>\n" +
            "    <To>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>CCCCUS33XXX</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </To>\n" +
            "    <BizMsgIdr>987</BizMsgIdr>\n" +
            "    <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "    <BizSvc>swift.cbprplus.02</BizSvc>\n" +
            "    <CreDt>2021-06-02T11:05:42.864+03:00</CreDt>\n" +
            "    <Prty>NORM</Prty>\n" +
            "</AppHdr>\n" +
            "\n" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>987</MsgId>\n" +
            "            <CreDtTm>2021-06-02T11:05:42.887+03:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>INDA</SttlmMtd>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>987</InstrId>\n" +
            "                <EndToEndId>NOTPROVIDED</EndToEndId>\n" +
            "                <UETR>c8b66b47-2bd9-48fe-be90-93c2096f27d2</UETR>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"USD\">10500.00</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2009-05-27</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <DbtDtTm>2009-05-27T12:49:00.000+02:00</DbtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <PrvsInstgAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CHASUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </PrvsInstgAgt1>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAABEBBXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCUS33XXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <IntrmyAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICD</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </IntrmyAgt1>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BKAUATWW</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICE</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <CdtrAgtAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAgtAcct>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICF</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAcct>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>\n";

    public static final String VALID_CBPR_TRANSLATOR_MX_TO_MT_RESPONSE = "{1:F01CCCCUS33XXXX0000000000}{2:O2021108210602AAAABEBBXXXX00000000002106021108N}{3:{121:c8b66b47-2bd9-48fe-be90-93c2096f27d2}}{4:\n" +
            ":20:987\n" +
            ":21:NOTPROVIDED\n" +
            ":13C:/SNDTIME/1249+0200\n" +
            ":32A:090527USD10500,\n" +
            ":52A:BKAUATWW\n" +
            ":56A:TESTBICD\n" +
            ":57A:/CDTRAGTACCT\n" +
            "TESTBICE\n" +
            ":58A:/CDTRACCT\n" +
            "TESTBICF\n" +
            ":72:/INS/CHASUS33\n" +
            "-}";

    public static final String VALID_CBPR_REQUEST = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n" +
            "    <Fr>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>BBBBUS33</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </Fr>\n" +
            "    <To>\n" +
            "        <FIId>\n" +
            "            <FinInstnId>\n" +
            "                <BICFI>CCCCJPJT</BICFI>\n" +
            "            </FinInstnId>\n" +
            "        </FIId>\n" +
            "    </To>\n" +
            "    <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "    <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "    <BizSvc>swift.cbprplus.02</BizSvc>\n" +
            "    <MktPrctc>\n" +
            "        <Regy>string</Regy>\n" +
            "        <Id>string</Id>\n" +
            "    </MktPrctc>\n" +
            "    <CreDt>2008-09-29T04:49:45+03:00</CreDt>\n" +
            "    <CpyDplct>CODU</CpyDplct>\n" +
            "    <PssblDplct>true</PssblDplct>\n" +
            "    <Prty>NORM</Prty>\n" +
            "    <Rltd>\n" +
            "        <Fr>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </Fr>\n" +
            "        <To>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </To>\n" +
            "        <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "        <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "        <BizSvc>swift.cbprplus.02</BizSvc>\n" +
            "        <CreDt>2014-06-09T18:15:04+03:00</CreDt>\n" +
            "        <CpyDplct>COPY</CpyDplct>\n" +
            "        <Prty>NORM</Prty>\n" +
            "    </Rltd>\n" +
            "</AppHdr>\n" +
            "\n" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>BBBB/120928-FICT/JPY/430</MsgId>\n" +
            "            <CreDtTm>2012-09-28T16:00:00+13:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>INDA</SttlmMtd>\n" +
            "                <SttlmAcct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>ACCOUNTID</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </SttlmAcct>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>BBBB/120928-FICT</InstrId>\n" +
            "                <EndToEndId>ABC/4562/2012-09-08</EndToEndId>\n" +
            "                <TxId>BBBB/120928-CCT/123/1</TxId>\n" +
            "                <UETR>00000000-0000-4000-8000-000000000000</UETR>\n" +
            "            </PmtId>\n" +
            "            <PmtTpInf>\n" +
            "                <InstrPrty>NORM</InstrPrty>\n" +
            "            </PmtTpInf>\n" +
            "            <IntrBkSttlmAmt Ccy=\"JPY\">10000000</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2012-09-29</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <CdtDtTm>2012-09-28T16:00:00+13:00</CdtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <SttlmTmReq>\n" +
            "                <CLSTm>12:12:12+13:00</CLSTm>\n" +
            "            </SttlmTmReq>\n" +
            "            <PrvsInstgAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>TESTBICD</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </PrvsInstgAgt1>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <IntrmyAgt1>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>INTERBIC</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </IntrmyAgt1>\n" +
            "            <IntrmyAgt1Acct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>INTERAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </IntrmyAgt1Acct>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                    <Nm>Debtor Name</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <AdrLine>Address</AdrLine>\n" +
            "                    </PstlAdr>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <DbtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>DBTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </DbtrAcct>\n" +
            "            <DbtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </DbtrAgt>\n" +
            "            <CdtrAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </CdtrAgt>\n" +
            "            <CdtrAgtAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRAGTACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAgtAcct>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>AAAAGB2L</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <CdtrAcct>\n" +
            "                <Id>\n" +
            "                    <Othr>\n" +
            "                        <Id>CDTRACCT</Id>\n" +
            "                    </Othr>\n" +
            "                </Id>\n" +
            "            </CdtrAcct>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>";

    public static final String CBPR_ENVELOPE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<RequestPayload>\n" +
            "    <AppHdr xmlns=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.02\">\n" +
            "        <Fr>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>BBBBUS33</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </Fr>\n" +
            "        <To>\n" +
            "            <FIId>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>CCCCJPJT</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </FIId>\n" +
            "        </To>\n" +
            "        <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "        <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "        <BizSvc>swift.cbprplus.02</BizSvc>\n" +
            "        <MktPrctc>\n" +
            "            <Regy>string</Regy>\n" +
            "            <Id>string</Id>\n" +
            "        </MktPrctc>\n" +
            "        <CreDt>2008-09-29T04:49:45+03:00</CreDt>\n" +
            "        <CpyDplct>CODU</CpyDplct>\n" +
            "        <PssblDplct>true</PssblDplct>\n" +
            "        <Prty>NORM</Prty>\n" +
            "        <Rltd>\n" +
            "            <Fr>\n" +
            "                <FIId>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>BBBBUS33</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </FIId>\n" +
            "            </Fr>\n" +
            "            <To>\n" +
            "                <FIId>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>CCCCJPJT</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </FIId>\n" +
            "            </To>\n" +
            "            <BizMsgIdr>BBBB/120928-FICT/JPY/430</BizMsgIdr>\n" +
            "            <MsgDefIdr>pacs.009.001.08</MsgDefIdr>\n" +
            "            <BizSvc>swift.cbprplus.02</BizSvc>\n" +
            "            <CreDt>2014-06-09T18:15:04+03:00</CreDt>\n" +
            "            <CpyDplct>COPY</CpyDplct>\n" +
            "            <Prty>NORM</Prty>\n" +
            "        </Rltd>\n" +
            "    </AppHdr>\n" +
            "    <Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "        <FICdtTrf>\n" +
            "            <GrpHdr>\n" +
            "                <MsgId>BBBB/120928-FICT/JPY/430</MsgId>\n" +
            "                <CreDtTm>2012-09-28T16:00:00+13:00</CreDtTm>\n" +
            "                <NbOfTxs>1</NbOfTxs>\n" +
            "                <SttlmInf>\n" +
            "                    <SttlmMtd>INDA</SttlmMtd>\n" +
            "                    <SttlmAcct>\n" +
            "                        <Id>\n" +
            "                            <Othr>\n" +
            "                                <Id>ACCOUNTID</Id>\n" +
            "                            </Othr>\n" +
            "                        </Id>\n" +
            "                    </SttlmAcct>\n" +
            "                </SttlmInf>\n" +
            "            </GrpHdr>\n" +
            "            <CdtTrfTxInf>\n" +
            "                <PmtId>\n" +
            "                    <InstrId>BBBB/120928-FICT</InstrId>\n" +
            "                    <EndToEndId>ABC/4562/2012-09-08</EndToEndId>\n" +
            "                    <TxId>BBBB/120928-CCT/123/1</TxId>\n" +
            "                    <UETR>00000000-0000-4000-8000-000000000000</UETR>\n" +
            "                </PmtId>\n" +
            "                <PmtTpInf>\n" +
            "                    <InstrPrty>NORM</InstrPrty>\n" +
            "                </PmtTpInf>\n" +
            "                <IntrBkSttlmAmt Ccy=\"JPY\">10000000</IntrBkSttlmAmt>\n" +
            "                <IntrBkSttlmDt>2012-09-29</IntrBkSttlmDt>\n" +
            "                <SttlmTmIndctn>\n" +
            "                    <CdtDtTm>2012-09-28T16:00:00+13:00</CdtDtTm>\n" +
            "                </SttlmTmIndctn>\n" +
            "                <SttlmTmReq>\n" +
            "                    <CLSTm>12:12:12+13:00</CLSTm>\n" +
            "                </SttlmTmReq>\n" +
            "                <PrvsInstgAgt1>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>TESTBICD</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </PrvsInstgAgt1>\n" +
            "                <InstgAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>BBBBUS33</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </InstgAgt>\n" +
            "                <InstdAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>CCCCJPJT</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </InstdAgt>\n" +
            "                <IntrmyAgt1>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>INTERBIC</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </IntrmyAgt1>\n" +
            "                <IntrmyAgt1Acct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>INTERAGTACCT</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </IntrmyAgt1Acct>\n" +
            "                <Dbtr>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>BBBBUS33</BICFI>\n" +
            "                        <Nm>Debtor Name</Nm>\n" +
            "                        <PstlAdr>\n" +
            "                            <AdrLine>Address</AdrLine>\n" +
            "                        </PstlAdr>\n" +
            "                    </FinInstnId>\n" +
            "                </Dbtr>\n" +
            "                <DbtrAcct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>DBTRACCT</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </DbtrAcct>\n" +
            "                <DbtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>BBBBUS33</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </DbtrAgt>\n" +
            "                <CdtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>AAAAJPJT</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </CdtrAgt>\n" +
            "                <CdtrAgtAcct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>CDTRAGTACCT</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </CdtrAgtAcct>\n" +
            "                <Cdtr>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>AAAAGB2L</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </Cdtr>\n" +
            "                <CdtrAcct>\n" +
            "                    <Id>\n" +
            "                        <Othr>\n" +
            "                            <Id>CDTRACCT</Id>\n" +
            "                        </Othr>\n" +
            "                    </Id>\n" +
            "                </CdtrAcct>\n" +
            "            </CdtTrfTxInf>\n" +
            "        </FICdtTrf>\n" +
            "    </Document>\n" +
            "</RequestPayload>\n";

    public static final String VALID_RTGS_PACS_009 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!--Inbound_pacs.009_RTGS_FICreditTransferOrder_COV_bs028-->\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08 RTGS_pacs_guidelines_pacs_009_FIToFIFinancialInstitutionCreditTransfer_pacs_009_001_08_20191021_1544%20(1).xsd\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>NONREF</MsgId>\n" +
            "            <CreDtTm>2019-10-07T13:45:00+00:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys>\n" +
            "                    <Cd>TGT</Cd>\n" +
            "                </ClrSys>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>Inp009b028-InsId</InstrId>\n" +
            "                <EndToEndId>Inp008b028-E2EId</EndToEndId>\n" +
            "                <UETR>e008b028-59c5-41e9-be4c-d45102fc201e</UETR>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"EUR\">61250.00</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2019-10-07</IntrBkSttlmDt>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFAC2</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFAC2</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "            <UndrlygCstmrCdtTrf>\n" +
            "                <UltmtDbtr>\n" +
            "                    <Nm>Ultimate debtor name</Nm>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>ULTMDBTRBIC</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </UltmtDbtr>\n" +
            "                <Dbtr>\n" +
            "                    <Nm>Debit customer name</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <TwnNm>Frankfurt</TwnNm>\n" +
            "                        <Ctry>DE</Ctry>\n" +
            "                    </PstlAdr>\n" +
            "                </Dbtr>\n" +
            "                <DbtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </DbtrAgt>\n" +
            "                <CdtrAgt>\n" +
            "                    <FinInstnId>\n" +
            "                        <BICFI>PBAADEFFXXX</BICFI>\n" +
            "                    </FinInstnId>\n" +
            "                </CdtrAgt>\n" +
            "                <Cdtr>\n" +
            "                    <Nm>Credit customer name</Nm>\n" +
            "                    <PstlAdr>\n" +
            "                        <TwnNm>Dusseldorf</TwnNm>\n" +
            "                        <Ctry>DE</Ctry>\n" +
            "                    </PstlAdr>\n" +
            "                </Cdtr>\n" +
            "                <UltmtCdtr>\n" +
            "                    <Nm>Ultimate creditor name</Nm>\n" +
            "                    <Id>\n" +
            "                        <OrgId>\n" +
            "                            <AnyBIC>ULTMCDTRBIC</AnyBIC>\n" +
            "                        </OrgId>\n" +
            "                    </Id>\n" +
            "                </UltmtCdtr>\n" +
            "            </UndrlygCstmrCdtTrf>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>";

    public static final String VALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST = "{1:F01PBAADEFFAC2X0000000000}{2:O2021109210323PBBBDEFFXXXX00000000002103231109N}{3:{121:e008b028-59c5-41e9-be4c-d45102fc201e}}{4:\n" +
            ":20:Inp009b028-InsId\n" +
            ":21:Inp009b028-E2EId\n" +
            ":13C:/RNCTIME/0915+0200\n" +
            ":32A:191007EUR61250,00\n" +
            ":52A:PBBBDEFFXXX\n" +
            ":58A:PBAADEFFAC2\n" +
            "-}";

    public static final String VALID_RTGS_TRANSLATOR_MT_TO_MX_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>Inp009b028-InsId</MsgId>\n" +
            "            <CreDtTm>2021-03-23T13:42:58.502+02:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys>\n" +
            "                    <Cd>TGT</Cd>\n" +
            "                </ClrSys>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>Inp009b028-InsId</InstrId>\n" +
            "                <EndToEndId>Inp009b028-E2EId</EndToEndId>\n" +
            "                <UETR>e008b028-59c5-41e9-be4c-d45102fc201e</UETR>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"EUR\">61250</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2019-10-07</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <CdtDtTm>2019-10-07T09:15:00.000+02:00</CdtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFC2X</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFAC2</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>\n";

    public static final String VALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08\">\n" +
            "    <FICdtTrf>\n" +
            "        <GrpHdr>\n" +
            "            <MsgId>Inp009b028-InsId</MsgId>\n" +
            "            <CreDtTm>2021-03-23T13:42:58.502+02:00</CreDtTm>\n" +
            "            <NbOfTxs>1</NbOfTxs>\n" +
            "            <SttlmInf>\n" +
            "                <SttlmMtd>CLRG</SttlmMtd>\n" +
            "                <ClrSys>\n" +
            "                    <Cd>TGT</Cd>\n" +
            "                </ClrSys>\n" +
            "            </SttlmInf>\n" +
            "        </GrpHdr>\n" +
            "        <CdtTrfTxInf>\n" +
            "            <PmtId>\n" +
            "                <InstrId>Inp009b028-InsId</InstrId>\n" +
            "                <EndToEndId>NOTPROVIDED</EndToEndId>\n" +
            "                <UETR>e008b028-59c5-41e9-be4c-d45102fc201e</UETR>\n" +
            "            </PmtId>\n" +
            "            <IntrBkSttlmAmt Ccy=\"EUR\">61250.00</IntrBkSttlmAmt>\n" +
            "            <IntrBkSttlmDt>2019-10-07</IntrBkSttlmDt>\n" +
            "            <SttlmTmIndctn>\n" +
            "                <CdtDtTm>2019-10-07T09:15:00.000+02:00</CdtDtTm>\n" +
            "            </SttlmTmIndctn>\n" +
            "            <InstgAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstgAgt>\n" +
            "            <InstdAgt>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFC2X</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </InstdAgt>\n" +
            "            <Dbtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBBBDEFFXXX</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Dbtr>\n" +
            "            <Cdtr>\n" +
            "                <FinInstnId>\n" +
            "                    <BICFI>PBAADEFFAC2</BICFI>\n" +
            "                </FinInstnId>\n" +
            "            </Cdtr>\n" +
            "        </CdtTrfTxInf>\n" +
            "    </FICdtTrf>\n" +
            "</Document>";

    public static final String VALID_RTGS_TRANSLATOR_MX_TO_MT_RESPONSE = "{1:F01PBAADEFFXC2X0000000000}{2:O2021343210323PBBBDEFFXXXX00000000002103231343S}{3:{103:TGT}{121:e008b028-59c5-41e9-be4c-d45102fc201e}}{4:\n" +
            ":20:Inp009b028-InsId\n" +
            ":21:NOTPROVIDED\n" +
            ":13C:/RNCTIME/0915+0200\n" +
            ":32A:191007EUR61250,00\n" +
            ":52A:PBBBDEFFXXX\n" +
            ":58A:PBAADEFFAC2\n" +
            "-}";

    public static final String INVALID_MT_101 = VALID_MT_101.replaceFirst(":20:.*\n", "");
    public static final String VALID_JSON_SEPA_PACS_008 = Utils.convertXmlToJson(VALID_SEPA_PACS_008, "Document");
    public static final String VALID_JSON_SEPA_EPC_PACS_002 = Utils.convertXmlToJson(VALID_SEPA_EPC_PACS_002, "Document");
    public static final String INVALID_SEPA_EPC_PACS_002 = VALID_SEPA_EPC_PACS_002.replaceFirst("\\s*<MsgId>.*</MsgId>", "");
    public static final String INVALID_MX_PACS_009 = VALID_MX_PACS_009.replaceFirst("\\s*<MsgId>.*</MsgId>", "");
    public static final String VALID_JSON_MX_PACS009 = Utils.convertXmlToJson(VALID_MX_PACS_009, "Document");
    public static final String INVALID_CBPR_TRANSLATOR_MT_TO_MX_REQUEST = VALID_CBPR_TRANSLATOR_MT_TO_MX_REQUEST.replaceFirst(":20:.*\n", "");
    public static final String INVALID_CBPR_TRANSLATOR_MX_TO_MT_REQUEST = VALID_CBPR_TRANSLATOR_MX_TO_MT_REQUEST.replaceFirst("\\s*<MsgId>.*</MsgId>", "");
    public static final String INVALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST = VALID_RTGS_TRANSLATOR_MT_TO_MX_REQUEST.replaceFirst(":20:.*\n", "");
    public static final String INVALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST = VALID_RTGS_TRANSLATOR_MX_TO_MT_REQUEST.replaceFirst("\\s*<MsgId>.*</MsgId>", "");
    public static final String INVALID_CBPR_REQUEST = VALID_CBPR_REQUEST.replaceFirst("\\s*<MsgId>.*</MsgId>", "");
    public static final String INVALID_RTGS_PACS_009 = VALID_RTGS_PACS_009.replaceFirst("\\s*<MsgId>.*</MsgId>", "");
    public static final String VALID_JSON_RTGS_PACS_009 = Utils.convertXmlToJson(VALID_RTGS_PACS_009, "Document");

}
