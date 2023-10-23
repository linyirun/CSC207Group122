package use_case.clear_users;

// TODO Complete me

public class ClearOutputData {

    private final String deletedAccounts;
    private boolean useCaseFailed;

    public ClearOutputData(String deletedAccounts, boolean useCaseFailed) {
        this.deletedAccounts = deletedAccounts;
        this.useCaseFailed = useCaseFailed;
    }
    public String getDeletedAccounts() {
        return deletedAccounts;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
