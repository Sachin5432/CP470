# Class Diagram - Android Assignments Application

The following class diagram outlines the architecture and components of the application, including activities, standalone logic helper classes, adapters, and layout relationships.

```mermaid
classDiagram
    class LoginActivity {
        # String ACTIVITY_NAME
        - EditText editTextEmail
        - EditText editTextPassword
        - Button loginButton
        - SharedPreferences sharedPreferences
        # onCreate(Bundle savedInstanceState)
        # onStart()
        # onResume()
        # onPause()
        # onStop()
        # onDestroy()
    }

    class LoginValidator {
        - Pattern EMAIL_PATTERN
        + isValidEmail(String email)$ boolean
        + isValidPassword(String password)$ boolean
    }

    class MainActivity {
        # String ACTIVITY_NAME
        - Button button
        - Button startChatButton
        # onCreate(Bundle savedInstanceState)
        + onOptionsItemSelected(MenuItem item) boolean
        # onActivityResult(int requestCode, int resultCode, Intent data)
    }

    class ListItemsActivity {
        # String ACTIVITY_NAME
        - int REQUEST_IMAGE_CAPTURE
        - int REQUEST_CAMERA_PERMISSION
        - ImageButton imageButton
        # onCreate(Bundle savedInstanceState)
        - launchCamera()
        + onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
        + onOptionsItemSelected(MenuItem item) boolean
        + finish()
        + print(String message)
    }

    class ChatWindow {
        # String ACTIVITY_NAME
        - ListView listView
        - EditText textInput
        - Button sendButton
        - ChatMessageManager chatMessageManager
        - ChatAdapter messageAdapter
        # onCreate(Bundle savedInstanceState)
        + onOptionsItemSelected(MenuItem item) boolean
    }

    class ChatAdapter {
        + getCount() int
        + getItem(int position) String
        + getView(int position, View convertView, ViewGroup parent) View
    }

    class ChatMessageManager {
        - List~String~ messages
        + addMessage(String message) boolean
        + getMessageCount() int
        + getMessage(int position) String
        + isIncoming(int position) boolean
        + isOutgoing(int position) boolean
        + getMessages() List~String~
        + clear()
    }

    LoginActivity --> LoginValidator : validates input via
    LoginActivity ..> MainActivity : launches Intent
    MainActivity ..> ListItemsActivity : launches for result
    MainActivity ..> ChatWindow : launches Intent
    ChatWindow *-- ChatAdapter : inner class adapter
    ChatWindow *-- ChatMessageManager : manages chat messages
    ChatAdapter ..> ChatMessageManager : queries data & layout type
```

## Overview of Classes

### Activities
- **`LoginActivity`**: Serves as the launcher entry point of the app. Handles user credentials input, restores saved email from `SharedPreferences`, delegates email/password validation to `LoginValidator`, saves default email on success, and navigates to `MainActivity`.
- **`MainActivity`**: Central navigation hub. Provides options to navigate to `ListItemsActivity` (expecting a result) and `ChatWindow`. Handles result toasts returned from `ListItemsActivity`. Uses `NavUtils` for Up navigation back to parent `LoginActivity`.
- **`ListItemsActivity`**: Displays interactive UI controls: an `ImageButton` for camera capture with permission checks (`CAMERA`), a `Switch` showing state toasts, and a `CheckBox` triggering a confirmation dialog that finishes the activity with a result back to `MainActivity`.
- **`ChatWindow`**: Hosts the chat user interface with an `EditText`, `sendButton`, and `ListView`. Uses `ChatMessageManager` for message collection and `ChatAdapter` for displaying incoming/outgoing row layouts.

### Logic Helpers (Decoupled Business Logic)
- **`LoginValidator`**: Pure Java static utility providing email regex matching and password validation rules without Android SDK dependencies, enabling 100% unit test coverage on JVM.
- **`ChatMessageManager`**: Decoupled domain class managing chat message list state, ignoring blank/empty messages, and calculating alternating row layout types (`isIncoming` vs `isOutgoing`), enabling 100% unit test coverage.
