<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xslt="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/Sale">
    <fo:root>
        <fo:layout-master-set>
            <fo:simple-page-master master-name="A4-portrait"
                                   page-height="29.7cm" page-width="21.0cm" margin="2cm">
                <fo:region-body/>
            </fo:simple-page-master>
        </fo:layout-master-set>
        <fo:page-sequence master-reference="A4-portrait">
            <fo:flow flow-name="xsl-region-body">

            <xsl:variable name="poster" select="Images/Poster/@Bytes"/>
            <xsl:variable name="posterContentType" select="Images/Poster/@ContentType"/>

                <xsl:message><xsl:value-of select="$poster"/></xsl:message>
                <xsl:message><xsl:value-of select="$posterContentType"/></xsl:message>
                <fo:block >
                <fo:external-graphic content-width="50mm" src="url('data:{$posterContentType};base64,{$poster}')"/>
                </fo:block>
                <fo:block-container text-align="center"
                                    start-indent="80mm"
                                    width="50%"
                                    padding="3mm">

                    <fo:block font-size="24pt" font-weight="bold" space-after="4mm" start-indent="0mm">
                        <xsl:value-of select="Event/@Name"/>
                    </fo:block>
                    <fo:block  font-size="14pt" space-after="4mm" start-indent="0mm">
                        <xsl:value-of select="Event/@Address"/>
                    </fo:block>
                    <fo:block space-after="2mm" start-indent="0mm">
                        Doors Open: <xsl:value-of select="Event/@DoorsOpen"/>
                    </fo:block>
                    <fo:block space-after="2mm" start-indent="0mm">
                        Event Starts: <xsl:value-of select="Event/@EventStarts"/>
                    </fo:block>

                </fo:block-container>

                <fo:block-container space-after="6mm" start-indent="80mm"
                                    width="50%" padding="3mm" border="dotted black 2pt">
                    <fo:block font-size="14pt" font-weight="bold" space-after="4mm"
                              start-indent="0mm">Order Information</fo:block>

                    <fo:block space-after="2mm" start-indent="0mm">
                        <xsl:value-of select="Customer/@Name"/>
                    </fo:block>
                    <fo:block space-after="2mm" start-indent="0mm">
                        <xsl:value-of select="Customer/@PhoneNumer"/>
                    </fo:block>
                    <fo:block space-after="2mm" start-indent="0mm">
                        <xsl:value-of select="Customer/@EmailAddress"/>
                    </fo:block>
                </fo:block-container>

                <fo:block-container space-after="6mm" start-indent="80mm" width="50%" padding="3mm">
                    <fo:block space-after="2mm" start-indent="0mm">
                        <fo:instream-foreign-object>
                            <xslt:variable name="uuid" select="UUID"/>
                            <barcode:barcode
                                    xmlns:barcode="http://barcode4j.krysalis.org/ns"
                                    message="{$uuid}">
                                <barcode:code128>
                                    <barcode:height>12mm</barcode:height>
                                </barcode:code128>
                            </barcode:barcode>
                        </fo:instream-foreign-object>
                    </fo:block>
                </fo:block-container>

            </fo:flow>
        </fo:page-sequence>
    </fo:root>
</xsl:template>
</xsl:stylesheet>