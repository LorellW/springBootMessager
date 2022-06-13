<#import "parts/common.ftl" as c>
<#import "parts/loginLogout.ftl" as l>

<@c.page>
${message?ifExists}
<@l.login "/login" false/>
</@c.page>