package com.bank.model; // Keep tests in the model package to exercise entity defaults directly

import org.junit.jupiter.api.Test; // Import JUnit test annotation

import static org.assertj.core.api.Assertions.assertThat; // Import fluent assertions for readable expectations

class ModelDefaultsTests {

    @Test
    void customerBuilderKeepsEntityDefaults() {
        Customer customer = Customer.builder().build(); // Build through Lombok to verify builder-created entities keep defaults

        assertThat(customer.getAccounts()).isEmpty(); // Accounts should start as an empty collection for safe relationship updates
        assertThat(customer.getRole()).isEqualTo("ROLE_USER"); // New customers should default to the user role
        assertThat(customer.isEnabled()).isTrue(); // New customers should be enabled by default
        assertThat(customer.getFailedAttempts()).isZero(); // Failed login count should start at zero
        assertThat(customer.isAccountLocked()).isFalse(); // New customers should not be locked by default
    }

    @Test
    void notificationBuilderKeepsEntityDefaults() {
        Notification notification = Notification.builder().build(); // Build through Lombok to verify notification defaults survive builder use

        assertThat(notification.isRead()).isFalse(); // Notifications should start unread unless explicitly marked read
    }

    @Test
    void verificationTokenBuilderKeepsEntityDefaults() {
        VerificationToken token = VerificationToken.builder().build(); // Build through Lombok to verify token defaults survive builder use

        assertThat(token.isUsed()).isFalse(); // Verification tokens should start unused unless explicitly consumed
    }
}
