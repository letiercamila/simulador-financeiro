package br.gov.caixa.simuladorfinanceiro.rest;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;

@Path("/api/simulacao")
public class SimuladorFInanceiroRest {

    @Inject
    SimulacaoService simulacaoService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Realizar simulação e persistir")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Simulação criada"),
            @APIResponse(responseCode = "400", description = "Requisição inválida"),
            @APIResponse(responseCode = "500", description = "Erro interno")
    })
    public Response simular(@Valid SimulacaoRequestDto request) {
        try {
            SimulacaoResponseDto response = simulacaoService.simularEPersistir(request);
            return Response.created(java.net.URI.create("/api/simulacao/" + response.getId()))
                    .entity(response)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSimulacao(@PathParam("id") Long id) {
        SimulacaoResponseDto dto = simulacaoService.findById(id);
        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dto).build();
    }
}