<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="floatingInput" placeholder="Login" name="username">
        <label for="floatingInput">User name</label>
    </div>
    <div class="form-floating">
        <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password">
        <label for="floatingPassword">Password</label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <div class="mr-2">
    <#if !isRegisterForm><a href="/registration">Add new User</a></#if>
    </div>
    <button type="submit" class="btn btn-primary mb-3"><#if isRegisterForm>Create<#else>Log in</#if></button>
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <button type="submit" class="btn btn-primary mb-0">Log out</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
</form>
</#macro>
