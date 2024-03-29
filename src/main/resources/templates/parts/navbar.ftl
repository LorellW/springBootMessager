<#include "security.ftl">
<#import "loginLogout.ftl" as l>

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">SpringBootMessager</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                    <#if user??>
                        <li class="nav-item">
                            <a class="nav-link" href="/root">Messages</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/user-messages/${currentUserId}">My Messages</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/user/profile">Profile</a>
                        </li>
                    </#if>
                <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User List</a>
                </li>
                </#if>
            </ul>
                <div class="navbar-text mr-3"><#if user??>${name}<#else>Guest</#if></div>
                <@l.logout />
        </div>
    </div>
</nav>