<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:template match="REPORT">
  
  <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <fo:layout-master-set>
       <fo:simple-page-master 
       page-height="29.7cm" page-width="24cm" 	margin-top="1.5cm" margin-bottom="1.5cm" 
	margin-left="1.54cm" margin-right="1.54cm" master-name="first">
	<fo:region-body margin-top="1.0cm" margin-bottom="1.0cm" />
	<fo:region-before extent="0.3cm"/>
	<fo:region-after extent="0.3cm"/>
	</fo:simple-page-master>
              
    </fo:layout-master-set>
	
    <fo:page-sequence master-reference="first">
	    <fo:static-content flow-name="xsl-region-before">
		<fo:block text-align="end" font-size="9pt" font-family="Times Roman" line-height="10pt" >
		   Custom Report <fo:page-number/>
		</fo:block>
	    </fo:static-content> 
	   <fo:static-content flow-name="xsl-region-after">
		<fo:block text-align="end" font-size="7pt" font-family="Times Roman" line-height="10pt" >
		      <xsl:value-of select="/root/agreement/license_identifier"/>
		 </fo:block>
	 </fo:static-content> 
       <fo:flow flow-name="xsl-region-body">
       <fo:table space-after.optimum="14pt">
            <fo:table-column column-width="1cm"/>
            <fo:table-column column-width="15cm"/>
              <fo:table-body font-size="8pt" font-family="Times Roman">
               <xsl:for-each select="/REPORT">
      		     	<fo:table-row line-height="11pt">
					     <fo:table-cell padding="2pt"  border=".1mm  solid white">
							<fo:block text-align="center"></fo:block>
					     </fo:table-cell>
		       		</fo:table-row>	
		       </xsl:for-each>	    
            </fo:table-body>
         </fo:table>
   	     <xsl:apply-templates/></fo:flow>
	</fo:page-sequence>
	</fo:root>
</xsl:template>	
<xsl:template match ="page-break">
  <fo:block font-size="14pt" font-weight="bold" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em" break-after="page"></fo:block>
</xsl:template>
<xsl:template match ="REPORTNAME">
	  <fo:block font-size="14pt" 
				font-family="Times Roman" 
				line-height="18pt"
				space-after.optimum="30pt"
				background-color="pink"
				color="black"
				text-align="start"> <xsl:value-of select="."/></fo:block>
</xsl:template>

<xsl:template match="REPORTBODY">
<fo:block font-size="12pt" 
		          font-family="Times Roman" 
		          line-height="15pt"
		          space-after.optimum="10pt"
		          text-align="start">
<fo:table >
	<xsl:for-each select="/REPORT/REPORTBODY/FIELDNAMES/FIELDNAME">		
			<fo:table-column column-width="45mm"/>
	</xsl:for-each>
	<fo:table-header>
		<fo:table-row>
			<xsl:for-each select="/REPORT/REPORTBODY/FIELDNAMES/FIELDNAME">		
				<fo:table-cell><fo:block font-size="15pt" text-align="end"><xsl:value-of select="."/></fo:block></fo:table-cell>
			</xsl:for-each>
		</fo:table-row>	
	</fo:table-header>
<fo:table-body>
<fo:table-row>
	<fo:table-cell><fo:block font-weight="bold" line-height="10pt"><fo:leader leader-pattern="rule"></fo:leader></fo:block></fo:table-cell>
	<fo:table-cell><fo:block font-weight="bold" line-height="10pt"><fo:leader leader-pattern="rule"></fo:leader></fo:block></fo:table-cell>
	<fo:table-cell><fo:block font-weight="bold" line-height="10pt"><fo:leader leader-pattern="rule"></fo:leader></fo:block></fo:table-cell>
	<fo:table-cell><fo:block font-weight="bold" line-height="10pt"><fo:leader leader-pattern="rule"></fo:leader></fo:block></fo:table-cell>
	<fo:table-cell><fo:block font-weight="bold" line-height="10pt"><fo:leader leader-pattern="rule"></fo:leader></fo:block></fo:table-cell>
</fo:table-row>
<xsl:for-each select="/REPORT/REPORTBODY/FIELDVALUES/FIELD">
	<fo:table-row>
		<xsl:for-each select="/REPORT/REPORTBODY/FIELDVALUES/FIELD/FIELDVALUE">
			<fo:table-cell><fo:block font-weight="bold" line-height="10pt"><xsl:value-of select="."/></fo:block></fo:table-cell>
		</xsl:for-each>
	</fo:table-row>
</xsl:for-each>
</fo:table-body>
</fo:table>	
</fo:block>
</xsl:template>
</xsl:stylesheet>