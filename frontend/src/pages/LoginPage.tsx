import { Button } from "@/components/common/Button";
import Container from "@/components/common/Container";
import Form from "@/components/common/Form";
import Input from "@/components/common/Input";
import { backendAddress, queryOptions } from "@/lib/constants";
import { loginAccountSchema } from "@/schemas/accountSchema";
import { TLoginAccount } from "@/types/account";
import { zodResolver } from "@hookform/resolvers/zod";
import { useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { useForm } from "react-hook-form";

const LoginPage = () => {
    const {
        register,
        handleSubmit,
        formState: { isSubmitting, errors },
    } = useForm<TLoginAccount>({
        resolver: zodResolver(loginAccountSchema),
        reValidateMode: "onBlur",
    });
    const queryClient = useQueryClient();

    // TODO: Display a Toast on success
    async function loginHandler(formData: TLoginAccount) {
        await axios.post(`${backendAddress}/api/v1/auth/login`, formData, {
            withCredentials: true,
        });
        queryClient.invalidateQueries({
            queryKey: queryOptions.account.queryKey,
        });
    }

    return (
        <Container className="text-left">
            <Form className="px-0" onSubmit={handleSubmit(loginHandler)}>
                <div>
                    <p className="large">EduHub</p>
                    <h2>Login</h2>
                </div>
                <Input
                    {...register("email")}
                    type="email"
                    id="email"
                    placeholder="Email"
                    label="Email"
                    errorLabel={errors.email && errors.email.message}
                />
                <Input
                    {...register("password")}
                    type="password"
                    id="password"
                    placeholder="Password"
                    label="Password"
                    errorLabel={errors.password && errors.password.message}
                />
                <Button type="submit" disabled={isSubmitting}>
                    Submit
                </Button>
            </Form>
        </Container>
    );
};

export default LoginPage;
