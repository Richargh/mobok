package de.richargh.mobok.git

import org.junit.jupiter.api.Disabled

@Disabled("Only work locally")
class FileSystemGitTest: GitContract(FileSystemGit())