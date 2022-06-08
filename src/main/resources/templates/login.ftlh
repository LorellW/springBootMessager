<#import "parts/common.ftl" as c>
<#import "parts/loginLogout.ftl" as l>

<@c.page>
Login page
<@l.login "/login" />
<a href="/registration">Add new User</a>
</@c.page>