package uo.ri.cws.application.repository;

import java.util.Optional;

import uo.ri.cws.domain.SparePart;

public interface SparePartRepository extends Repository<SparePart> {

    Optional<SparePart> findByCode(String code);

}
