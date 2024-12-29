import { Button } from "@/components/common/Button";
import Container from "@/components/common/Container";
import Form from "@/components/common/Form";
import Input from "@/components/common/Input";
import { accountSchema } from "@/schemas/accountSchema";
import { TAccount } from "@/types/account";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";

const LoginPage = () => {
    const {
        register,
        handleSubmit,
        formState: { isSubmitting, errors },
    } = useForm<TAccount>({
        resolver: zodResolver(accountSchema),
        reValidateMode: "onBlur",
    });

    function signupHandler(formData: TAccount) {
        console.log("Submitting data");
        console.log(formData);
        setTimeout(() => {
            console.log("Done.");
        }, 2000);
    }

    return (
        <Container className="text-left">
            <Form className="px-0" onSubmit={handleSubmit(signupHandler)}>
                <div>
                    <p className="large">EduHub</p>
                    <h2>Login</h2>
                </div>
                <div className="flex w-full">
                    <Input
                        {...register("firstName")}
                        type="text"
                        id="firstName"
                        placeholder="First Name"
                        className="rounded-r-none"
                        label="First Name"
                        errorLabel={
                            errors.firstName && errors.firstName.message
                        }
                    />
                    <Input
                        {...register("lastName")}
                        id="lastName"
                        type="text"
                        placeholder="Last Name"
                        className="rounded-l-none"
                        label="Last Name"
                        errorLabel={errors.lastName && errors.lastName.message}
                    />
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
                <Input
                    {...register("repeatPassword")}
                    type="password"
                    id="passwordRepeat"
                    placeholder="Repeat Password"
                    label="Repeat Password"
                    errorLabel={
                        errors.repeatPassword && errors.repeatPassword.message
                    }
                />
                <Button type="submit" disabled={isSubmitting}>
                    Submit
                </Button>
            </Form>
        </Container>
    );
};

export default LoginPage;
