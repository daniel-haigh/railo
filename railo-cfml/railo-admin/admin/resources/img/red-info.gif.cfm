<cfset c='R0lGODlhCQAJALMAAN4xMf///////////////////////////////////////////////////////////yH5BAEAAAEALAAAAAAJAAkAAAQUEMhJg71BXrA5thrYbaMIeh+lAhEAOw=='><cfif getBaseTemplatePath() EQ getCurrentTemplatePath()><!---
	
	---><cfsilent>
	<cfapplication name="HTTPCaching" sessionmanagement="no" clientmanagement="no" applicationtimeout="#createtimespan(1,0,0,0)#" />
	<cfif not structKeyExists(application, "oHTTPCaching")>
		<cfset application.oHTTPCaching = createObject("component", "../HTTPCaching") />
	</cfif>
	
	<!--- the string to be used as an Etag - in the response header --->
	<cfset etag = "89F43038F5D1E3A0E55A9AB62795CCC8" />
	<cfset mimetype = "image/gif" />
	
	<!--- check if the content was cached on the browser, and set the ETag header. --->
	<cfif application.oHTTPCaching.handleResponseWhenCached(fileEtag=etag, mimetype=mimetype, expireDays=100)>
		<cfexit method="exittemplate" />
	</cfif>
</cfsilent>

<!--- file was not cached; send the data --->
<cfcontent reset="yes" type="#mimetype#"
	variable="#toBinary(c)#" />
<cfelse>data:image/image/gif;base64,<cfoutput>#c#</cfoutput></cfif>
	
