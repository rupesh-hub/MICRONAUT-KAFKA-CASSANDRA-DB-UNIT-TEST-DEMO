package com.rupesh.shared;

@FunctionalInterface
public interface UseCase<I extends UseCaseRequest, O extends UseCaseResponse> {
    O execute(I request);
}
