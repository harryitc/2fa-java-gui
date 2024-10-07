package lib_totp.time;

import java.time.Instant;
import lib_totp.exceptions.TimeProviderException;

public class SystemTimeProvider implements TimeProvider {
    @Override
    public long getTime() throws TimeProviderException {
        return Instant.now().getEpochSecond();
    }
}
