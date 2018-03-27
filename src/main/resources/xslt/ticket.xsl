<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
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
            <xsl:variable name="advert" select="Images/Advert/@Bytes"/>
            <xsl:variable name="advertContentType" select="Images/Advert/@ContentType"/>
            <xsl:variable name="logo" select="Images/Logo/@Bytes"/>
            <xsl:variable name="logoContentType" select="Images/Logo/@ContentType"/>

                <fo:block-container>
                    <fo:block text-align="right">
                        <xsl:value-of  select="RenderDateTime"/>
                    </fo:block>
                </fo:block-container>

                <fo:block-container text-align="center">
                <fo:block>
                    <fo:external-graphic content-width="40mm" src="url('data:{$logoContentType};base64,{$logo}')"/>
                </fo:block>
            </fo:block-container>

                <fo:table space-after="6mm">
                    <fo:table-body>
                        <fo:table-row>
                            <fo:table-cell text-align="center">
                                <fo:block space-after="6mm">
                                    <fo:external-graphic content-width="70mm" src="url('data:{$posterContentType};base64,{$poster}')"/>
                                </fo:block>
                                <fo:block space-after="6mm">
                                    <fo:external-graphic content-width="70mm" src="url('data:{$advertContentType};base64,{$advert}')"/>
                                </fo:block>
                            </fo:table-cell>

                            <fo:table-cell text-align="center">

                                <fo:block-container space-after="6mm">

                                    <fo:block font-size="24pt" font-weight="bold" space-after="4mm">
                                        <xsl:value-of select="Event/@Name"/>
                                    </fo:block>
                                    <fo:block  font-size="14pt" space-after="4mm">
                                        <xsl:value-of select="Event/@Address"/>
                                    </fo:block>
                                    <fo:block space-after="2mm" start-indent="0mm">
                                        Doors Open: <xsl:value-of select="Event/@DoorsOpen"/>
                                    </fo:block>
                                    <fo:block space-after="2mm" start-indent="0mm">
                                        Event Starts: <xsl:value-of select="Event/@EventStarts"/>
                                    </fo:block>
                                </fo:block-container>

                                <fo:block-container space-after="6mm" padding="3mm" border="dotted black 2pt">
                                    <fo:block font-size="14pt" font-weight="bold" space-after="4mm">Order Information</fo:block>

                                    <fo:block space-after="2mm">
                                        <xsl:value-of select="Customer/@Name"/>
                                    </fo:block>
                                    <fo:block space-after="2mm">
                                        <xsl:value-of select="Customer/@PhoneNumer"/>
                                    </fo:block>
                                    <fo:block space-after="2mm">
                                        <xsl:value-of select="Customer/@EmailAddress"/>
                                    </fo:block>

                                        <fo:table border-collapse="separate" border-separation="5pt">
                                            <fo:table-column column-width="60mm"/>
                                            <fo:table-column column-width="25mm"/>

                                            <fo:table-body>
                                                <xsl:for-each select="Tickets/Ticket">
                                                <fo:table-row space-after="4mm">
                                                    <fo:table-cell text-align="left">
                                                        <fo:block><xsl:value-of select="@Name"/></fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell text-align="right">
                                                        <fo:block><xsl:value-of select="@Quantity"/> @ $<xsl:value-of select="@Price"/></fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                                </xsl:for-each>
                                                <fo:table-row>
                                                    <fo:table-cell text-align="left">
                                                        <fo:block>Taxes and Fees</fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell text-align="right">
                                                        <fo:block>$<xsl:value-of select="Totals/@FeesAndTaxes"/></fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell>
                                                    <fo:block text-align="left" font-weight="bold">Total</fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell text-align="right">
                                                    <fo:block>$<xsl:value-of select="Totals/@TotalAfterFeesAndTaxes"/></fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            </fo:table-body>
                                        </fo:table>

                                </fo:block-container>

                                <fo:block-container space-after="6mm" padding="3mm">
                                    <fo:block space-after="2mm">
                                        <fo:instream-foreign-object>
                                            <barcode:barcode
                                                    xmlns:barcode="http://barcode4j.krysalis.org/ns"
                                                    message="{UUID}">
                                                <barcode:code128>
                                                    <barcode:height>12mm</barcode:height>
                                                </barcode:code128>
                                            </barcode:barcode>
                                        </fo:instream-foreign-object>
                                    </fo:block>
                                </fo:block-container>

                            </fo:table-cell>
                        </fo:table-row>
                    </fo:table-body>
                </fo:table>

                <fo:block-container>
                    <fo:block text-align="center" space-before="10mm" font-size="16pt" font-weight="bold">Ticket Policies &amp; Information</fo:block>
                    <fo:block space-before="4mm" font-size="12pt">
                        All reservations must be secured with an credit card and tickets may be picked up at the Box Office the day of the fight. You will need a state issued ID at the box office to pick up Online Tickets.
                        All sales are final and will be charged at the time the reservation is made. Show times may vary from production to production. For specifics dates and times, please contact us at <xsl:value-of select="Coordinator/@Phone"/> or <xsl:value-of select="Coordinator/@Email"/>.
                        For reservations made online, a service fee is added to each ticket.
                    </fo:block>
                </fo:block-container>

            </fo:flow>
        </fo:page-sequence>
    </fo:root>
</xsl:template>
</xsl:stylesheet>