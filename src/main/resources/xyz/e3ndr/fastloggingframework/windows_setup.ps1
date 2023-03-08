# Adapted from https://github.com/rprichard/winpty/blob/7e59fe2d09adf0fa2aa606492e7ca98efbc5184e/misc/ConinMode.ps1

$signature = @'
[DllImport("kernel32.dll", SetLastError = true)]
public static extern IntPtr GetStdHandle(int nStdHandle);

[DllImport("kernel32.dll", SetLastError = true)]
public static extern uint GetConsoleMode(
    IntPtr hConsoleHandle,
    out uint lpMode
);
    
[DllImport("kernel32.dll", SetLastError = true)]
public static extern uint SetConsoleMode(
    IntPtr hConsoleHandle,
    uint dwMode
);

public const int STD_OUTPUT_HANDLE = -11;
public const int ENABLE_VIRTUAL_TERMINAL_PROCESSING  = 0x0004;
'@

$WinAPI = Add-Type -MemberDefinition $signature `
    -Name WinAPI -Namespace ConinModeScript `
    -PassThru

$handle = $WinAPI::GetStdHandle($WinAPI::STD_OUTPUT_HANDLE)
$mode = 0

$ret = $WinAPI::GetConsoleMode($handle, [ref]$mode)
if ($ret -eq 0) {
    throw "GetConsoleMode failed (is stdin a console?)"
}

$ret = $WinAPI::SetConsoleMode($handle, $mode -bor $WinAPI::ENABLE_VIRTUAL_TERMINAL_PROCESSING)
if ($ret -eq 0) {
    throw "GetConsoleMode failed (is stdin a console?)"
}
