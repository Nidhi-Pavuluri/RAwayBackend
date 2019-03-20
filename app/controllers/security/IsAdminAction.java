package controllers.security;

import models.User;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class IsAdminAction extends Action.Simple {

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {

        final User user = (User) ctx.args.get("user");
        if (user.getRole() != User.Role.ADMIN) {
            return CompletableFuture.completedFuture(forbidden());
        }

        Logger.debug("User has admin role");

        return delegate.call(ctx);


    }

}