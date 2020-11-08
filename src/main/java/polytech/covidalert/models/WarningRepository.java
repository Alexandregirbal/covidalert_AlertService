package polytech.covidalert.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarningRepository extends JpaRepository<Warning,Long> {
}