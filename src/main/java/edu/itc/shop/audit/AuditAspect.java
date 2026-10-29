package edu.itc.shop.audit;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect // marks the class as an aspect
@Component // lets Spring find it
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    private final RequestContext context;

    public AuditAspect(RequestContext context) {
        this.context = context;
    }

    @Around("@annotation(edu.itc.shop.audit.Audited)") 
    public Object audit(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().getDeclaringType().getSimpleName()
                + "." + pjp.getSignature().getName();
        String args = Arrays.toString(pjp.getArgs());
        long start = System.nanoTime();
        try {
            Object result = pjp.proceed();
            log.info("AUDIT {} requestId={} args={} result={} ({} ms)",
                    method, requestId(), args, result, elapsedMs(start));
            return result;
        } catch (Throwable ex) {
            log.warn("AUDIT {} requestId={} args={} failed={} ({} ms)",
                    method, requestId(), args, ex.toString(), elapsedMs(start));
            throw ex;
        }
    }

    private String requestId() {
        return RequestContextHolder.getRequestAttributes() == null ? "-" : context.getRequestId();
    }

    private long elapsedMs(long startNanos) {
        return (System.nanoTime() - startNanos) / 1_000_000;
    }
}