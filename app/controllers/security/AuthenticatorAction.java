package controllers.security;

import com.google.inject.Inject;
import daos.Userdao;
import models.User;
import play.Logger;
import play.db.jpa.JPAApi;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AuthenticatorAction extends Action.Simple {

    private final static Logger.ALogger LOGGER = Logger.of(AuthenticatorAction.class);

    private Userdao userDao;
    private JPAApi jpaApi;

    @Inject
    public AuthenticatorAction(Userdao userDao, JPAApi jpaApi) {
        this.userDao = userDao;
        this.jpaApi = jpaApi;
    }

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {

        final Optional<String> authHeader = ctx.request().header("Authorization");
        if (!authHeader.isPresent()) {
            return CompletableFuture.completedFuture(unauthorized("Go and sign in"));
        }

        LOGGER.debug("Auth token = {}", authHeader.get());

        if (!authHeader.get().startsWith("Bearer ")) {
            return CompletableFuture.completedFuture(unauthorized("Invalid auth header format"));
        }

        final String accessToken = authHeader.get().substring(7);
        LOGGER.debug("accessToken {}", accessToken);
        if (accessToken.isEmpty()) {
            return CompletableFuture.completedFuture(unauthorized("Invalid auth header format"));
        }

        final User user = jpaApi.withTransaction(em ->
                userDao.findUserByAuthToken(accessToken)
        );

        if (null == user) {
            return CompletableFuture.completedFuture(unauthorized("User not found"));
        }

        ctx.args.put("user", user);

        return delegate.call(ctx);
    }

}