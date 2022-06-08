<#import "parts/common.ftl" as c>
<#import "parts/loginLogout.ftl" as l>

<@c.page>
Add new user
${message!"default message"}
<@l.login "/registration" />
</@c.page>