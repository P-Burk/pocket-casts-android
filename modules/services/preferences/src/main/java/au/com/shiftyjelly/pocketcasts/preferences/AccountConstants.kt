package au.com.shiftyjelly.pocketcasts.preferences

import android.accounts.Account
import android.accounts.AccountManager

object AccountConstants {
    val ACCOUNT_TYPE = (if (BuildConfig.DEBUG) "au.com.shiftyjelly.pocketcasts.debug" else "au.com.shiftyjelly.pocketcasts") + ".pocketcasts"
    const val TOKEN_TYPE = "sync"
    const val UUID = "uuid"
    const val CLIENT_ID = "client_id"
    const val CLIENT_ID_POCKET_CASTS = "api.pocketcasts.com"
}

fun AccountManager.pocketCastsAccount(): Account? {
    return getAccountsByType(AccountConstants.ACCOUNT_TYPE).firstOrNull()
}
