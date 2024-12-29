export function getAccountInitials(firstName: string, lastName: string) {
    return (firstName.charAt(0) + lastName.charAt(0)).toUpperCase();
}

export function getAccountInitialsFullName(fullName: string) {
    const [firstName, lastName] = fullName.split(" ");
    try {
        return (firstName.charAt(0) + lastName.charAt(0)).toUpperCase();
    } catch {
        return fullName.charAt(0).toUpperCase();
    }
}
