<#import "parts/common.ftl" as c>

<@c.page>
<h5>${username}</h5>
${message?ifExists}
<form method="post">
    <div class="form-floating">
        <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password"/>
        <label for="floatingPassword">Password</label>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="form-floating">
        <input type="email" class="form-control" id="email" placeholder="some@some.com" name="email" value="${email!''}"/>
        <label for="floatingPassword">Email</label>
    </div>
<button type="submit" class="btn btn-primary mb-3">Save</button>
</form>
</@c.page>